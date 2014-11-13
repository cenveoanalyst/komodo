/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package org.teiid.client.batch;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamConstants;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.komodo.spi.runtime.version.TeiidVersion;
import org.teiid.client.BatchSerializer;
import org.teiid.client.ResizingArrayList;
import org.teiid.core.types.BlobType;
import org.teiid.core.types.ClobType;
import org.teiid.core.types.DefaultDataTypeManager;
import org.teiid.core.types.DefaultDataTypeManager.DefaultDataTypes;
import org.teiid.core.types.XMLType;
import org.teiid.runtime.client.Messages;



/** 
 *
 */
public class Batch0Serializer extends BatchSerializer {

    private ColumnSerializer defaultSerializer = new ColumnSerializer();
    
    private final Map<String, ColumnSerializer> serializers = new HashMap<String, ColumnSerializer>(128);

    private final Map<String, ColumnSerializer> version1serializers = new HashMap<String, ColumnSerializer>(128);

    /**
     * @param teiidVersion
     */
    public Batch0Serializer(TeiidVersion teiidVersion) {
        super(teiidVersion);

        serializers.put(DefaultDataTypeManager.DefaultDataTypes.BIG_DECIMAL.getId(),   new BigDecimalColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.BIG_INTEGER.getId(),   new BigIntegerColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.BOOLEAN.getId(),       new BooleanColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.BYTE.getId(),          new ByteColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.CHAR.getId(),          new CharColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.DATE.getId(),          new DateColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.DOUBLE.getId(),        new DoubleColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.FLOAT.getId(),         new FloatColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.INTEGER.getId(),       new IntColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.LONG.getId(),          new LongColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.SHORT.getId(),         new ShortColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.TIME.getId(),          new TimeColumnSerializer());
        serializers.put(DefaultDataTypeManager.DefaultDataTypes.TIMESTAMP.getId(),     new TimestampColumnSerializer());

        version1serializers.put(DefaultDataTypeManager.DefaultDataTypes.DATE.getId(),          new DateColumnSerializer1());
    	version1serializers.put(DefaultDataTypeManager.DefaultDataTypes.TIME.getId(),          new TimeColumnSerializer1());
    	version1serializers.put(DefaultDataTypeManager.DefaultDataTypes.STRING.getId(),     	new StringColumnSerializer1());
    	version1serializers.put(DefaultDataTypeManager.DefaultDataTypes.CLOB.getId(),     		new ClobColumnSerializer1());
    	version1serializers.put(DefaultDataTypeManager.DefaultDataTypes.BLOB.getId(),     		new BlobColumnSerializer1());
    	version1serializers.put(DefaultDataTypeManager.DefaultDataTypes.XML.getId(),     		new XmlColumnSerializer1());
    	version1serializers.put(DefaultDataTypeManager.DefaultDataTypes.NULL.getId(),     		new NullColumnSerializer1());
    	version1serializers.put(DefaultDataTypeManager.DefaultDataTypes.OBJECT.getId(),     	new ObjectColumnSerializer1());
    }
    
    public final class ObjectColumnSerializer1 extends ColumnSerializer {
    	
    	@Override
    	protected void writeObject(ObjectOutput out, Object obj)
    			throws IOException {
    	    DefaultDataTypes dataType =  getDataTypeManager().getDataType(obj.getClass());
            int code = dataType.ordinal();
    		out.writeByte((byte)code);
    		if (code == DefaultDataTypeManager.DefaultDataTypes.BOOLEAN.ordinal()) {
    			if (Boolean.TRUE.equals(obj)) {
    				out.write((byte)1);
    			} else {
    				out.write((byte)0);
    			}
    		} else if (code != DefaultDataTypeManager.DefaultDataTypes.OBJECT.ordinal()) {
    		    dataType = DefaultDataTypeManager.DefaultDataTypes.valueOf(getTeiidVersion(), code);
    			ColumnSerializer s = getSerializer(dataType.getId(), (byte)1);
    			s.writeObject(out, obj);
    		} else {
    			super.writeObject(out, obj);
    		}
    	}
    	
    	@Override
    	protected Object readObject(ObjectInput in) throws IOException,
    			ClassNotFoundException {
    		int code = in.readByte();
    		if (code == DefaultDataTypeManager.DefaultDataTypes.BOOLEAN.ordinal()) {
    			if (in.readByte() == (byte)0) {
    				return Boolean.FALSE;
    			}
    			return Boolean.TRUE;
    		}
    		if (code != DefaultDataTypeManager.DefaultDataTypes.OBJECT.ordinal()) {
    		    DefaultDataTypes dataType = DefaultDataTypeManager.DefaultDataTypes.valueOf(getTeiidVersion(), code);
    			ColumnSerializer s = getSerializer(dataType.getId(), (byte)1);
    			return s.readObject(in);
    		}
			return super.readObject(in);
    	}
    	
    }
    
    private final int MAX_UTF = 0xFFFF/3; //this is greater than the expected max length of Teiid Strings
    
    private class StringColumnSerializer1 extends ColumnSerializer {
    	@Override
    	protected void writeObject(ObjectOutput out, Object obj) throws IOException {
    		String str = (String)obj;
        	if (str.length() <= MAX_UTF) {
        		//skip object serialization if we have a short string
        	    out.writeByte(ObjectStreamConstants.TC_STRING);
        	    out.writeUTF(str);
        	} else {
        		out.writeByte(ObjectStreamConstants.TC_LONGSTRING);
        		out.writeObject(obj);
        	}
        }
    	
    	@Override
    	protected Object readObject(ObjectInput in) throws IOException,
    			ClassNotFoundException {
    		if (in.readByte() == ObjectStreamConstants.TC_STRING) {
    			return in.readUTF();
    		}
    		return super.readObject(in);
    	}
    }

    private class NullColumnSerializer1 extends ColumnSerializer {
    	@Override
    	public void writeColumn(ObjectOutput out, int col,
    			List<? extends List<?>> batch) throws IOException {
    	}
    	
    	@Override
    	public void readColumn(ObjectInput in, int col,
    			List<List<Object>> batch, byte[] isNull) throws IOException,
    			ClassNotFoundException {
    	}
    }
    
    private class ClobColumnSerializer1 extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
        	((Externalizable)obj).writeExternal(out);
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        	ClobType ct = new ClobType();
        	ct.readExternal(in);
            return ct;
        }
    }

    private class BlobColumnSerializer1 extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
        	((Externalizable)obj).writeExternal(out);
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        	BlobType bt = new BlobType();
        	bt.readExternal(in);
            return bt;
        }
    }

    private class XmlColumnSerializer1 extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
        	((Externalizable)obj).writeExternal(out);
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        	XMLType xt = new XMLType();
        	xt.readExternal(in);
            return xt;
        }
    }

    /**
     * Packs the (boolean) information about whether data values in the column are null
     * into bytes so that we send ~n/8 instead of n bytes.
     * @param out
     * @param col
     * @param batch
     * @throws IOException
     *
     */
    void writeIsNullData(ObjectOutput out, int col, List<? extends List<?>> batch) throws IOException {
        int numBytes = batch.size() / 8, row = 0, currentByte = 0;
        for (int byteNum = 0; byteNum < numBytes; byteNum++, row+=8) {
            currentByte  = (batch.get(row).get(col) == null) ? 0x80 : 0;
            if (batch.get(row+1).get(col) == null) currentByte |= 0x40;
            if (batch.get(row+2).get(col) == null) currentByte |= 0x20;
            if (batch.get(row+3).get(col) == null) currentByte |= 0x10;
            if (batch.get(row+4).get(col) == null) currentByte |= 0x08;
            if (batch.get(row+5).get(col) == null) currentByte |= 0x04;
            if (batch.get(row+6).get(col) == null) currentByte |= 0x02;
            if (batch.get(row+7).get(col) == null) currentByte |= 0x01;
            out.write(currentByte);
        }
        if (batch.size() % 8 > 0) {
            currentByte = 0;
            for (int mask = 0x80; row < batch.size(); row++, mask >>= 1) {
                if (batch.get(row).get(col) == null) currentByte |= mask;
            }
            out.write(currentByte);
        }
    }
    /**
     * Reads the isNull data into a byte array 
     * @param in
     * @param isNullBytes
     * @throws IOException
     *
     */
    void readIsNullData(ObjectInput in, byte[] isNullBytes) throws IOException {
        for (int i = 0; i < isNullBytes.length; i++) {
            isNullBytes[i] = in.readByte();
        }
    }
    
    /**
     * Gets whether a data value is null based on a packed byte array containing boolean data 
     * @param isNull
     * @param row
     * @return
     *
     */
    boolean isNullObject(byte[] isNull, int row) {
        //              byte number           mask     bits to shift mask
        return (isNull [ row / 8 ]         & (0x01 << (7 - (row % 8))))   != 0;
    }
    
    /**
     * An abstract serializer for native types
     *
     */
    private class ColumnSerializer {
        public void writeColumn(ObjectOutput out, int col, List<? extends List<?>> batch) throws IOException {
            writeIsNullData(out, col, batch);
            Object obj = null;
            for (int i = 0; i < batch.size(); i++) {
                obj = batch.get(i).get(col);
                if (obj != null) {
                    writeObject(out, obj);
                }
            }
        }
        
        public void readColumn(ObjectInput in, int col, List<List<Object>> batch, byte[] isNull) throws IOException, ClassNotFoundException {
            readIsNullData(in, isNull);
            for (int i = 0; i < batch.size(); i++) {
                if (!isNullObject(isNull, i)) {
                    batch.get(i).set(col, readObject(in));
                }
            }
        }
        
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
        	out.writeObject(obj);
        }
        protected Object readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        	return in.readObject();
        }
    }
    
    private class IntColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeInt(((Integer)obj).intValue());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return Integer.valueOf(in.readInt());
        }
    }
    
    private class LongColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeLong(((Long)obj).longValue());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return Long.valueOf(in.readLong());
        }
    }
    
    private class FloatColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeFloat(((Float)obj).floatValue());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return new Float(in.readFloat());
        }
    }
    
    private class DoubleColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeDouble(((Double)obj).doubleValue());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return new Double(in.readDouble());
        }
    }
    
    private class ShortColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeShort(((Short)obj).shortValue());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return Short.valueOf(in.readShort());
        }
    }
    
    private class BooleanColumnSerializer extends ColumnSerializer {
        /* This implementation compacts the isNull and boolean data for non-null values into a byte[]
         * by using a 8 bit mask that is bit-shifted to mask each value.
         */
    	@Override
        public void writeColumn(ObjectOutput out, int col, List<? extends List<?>> batch) throws IOException {
            int currentByte = 0;
            int mask = 0x80;
            Object obj;
            for (int row = 0; row < batch.size(); row++) {
                // Write the isNull value
                obj = batch.get(row).get(col);
                if (obj == null ) {
                    currentByte |= mask;
                }
                mask >>= 1; // Shift the mask to the next bit
                if (mask == 0) {
                    // If the current byte has been used up, write it and reset.
                    out.write(currentByte);
                    currentByte = 0;
                    mask = 0x80;
                }
                if (obj != null) {
                    // Write the boolean value if it's not null
                    if (((Boolean)obj).booleanValue()) {
                        currentByte |= mask;
                    }
                    mask >>= 1;
                    if (mask == 0) {
                        out.write(currentByte);
                        currentByte = 0;
                        mask = 0x80;
                    }
                }
            }
            // Invariant mask != 0
            // If we haven't reached the eight-row mark then the loop would not have written this byte
            // Write the final byte containing data for th extra rows, if it exists.
            if (mask != 0x80) {
                out.write(currentByte);
            }
        }
        
        @Override
        public void readColumn(ObjectInput in, int col,
        		List<List<Object>> batch, byte[] isNull) throws IOException,
        		ClassNotFoundException {
            int currentByte = 0, mask = 0; // Initialize the mask so that it is reset in the loop
            boolean isNullVal;
            for (int row = 0; row < batch.size(); row++) {
                if (mask == 0) {
                    // If we used up the byte, read the next one, and reset the mask
                    currentByte = in.read();
                    mask = 0x80;
                }
                isNullVal = (currentByte & mask) != 0;
                mask >>= 1; // Shift the mask to the next bit
                if (!isNullVal) {
                    if (mask == 0) {
                        currentByte = in.read();
                        mask = 0x80;
                    }
                    batch.get(row).set(col, ((currentByte & mask) == 0) ? Boolean.FALSE : Boolean.TRUE);
                    mask >>= 1;
                }
            }
        }
    }
    
    private class ByteColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeByte(((Byte)obj).byteValue());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return Byte.valueOf(in.readByte());
        }
    }
    
    private class CharColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeChar(((Character)obj).charValue());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return Character.valueOf(in.readChar());
        }
    }
    
    private class BigIntegerColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            BigInteger val = (BigInteger)obj;
            byte[] bytes = val.toByteArray();
            out.writeInt(bytes.length);
            out.write(bytes);
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            int length = in.readInt();
            byte[] bytes = new byte[length];
            in.readFully(bytes);
            return new BigInteger(bytes);
        }
    }
    
    private class BigDecimalColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            BigDecimal val = (BigDecimal)obj;
            out.writeInt(val.scale());
            BigInteger unscaled = val.unscaledValue();
            byte[] bytes = unscaled.toByteArray();
            out.writeInt(bytes.length);
            out.write(bytes);
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            int scale = in.readInt();
            int length = in.readInt();
            byte[] bytes = new byte[length];
            in.readFully(bytes);
            return new BigDecimal(new BigInteger(bytes), scale);
        }
    }
    
    private class DateColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeLong(((java.sql.Date)obj).getTime());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return new java.sql.Date(in.readLong());
        }
    }
    
    private class TimeColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeLong(((Time)obj).getTime());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return new Time(in.readLong());
        }
    }
    
    int DATE_NORMALIZER = 0;
    	
	{
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT")); //$NON-NLS-1$
		c.set(1900, 0, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		DATE_NORMALIZER = -(int)(c.getTime().getTime()/60000); //support a 32 bit range starting at this value
	}

    private class DateColumnSerializer1 extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeInt((int)(((java.sql.Date)obj).getTime()/60000) + DATE_NORMALIZER);
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return new java.sql.Date(((in.readInt()&0xffffffffL) - DATE_NORMALIZER)*60000);
        }
    }
    
    private class TimeColumnSerializer1 extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            out.writeInt((int)(((Time)obj).getTime()/1000));
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            return new Time((in.readInt()&0xffffffffL)*1000);
        }
    }
    
    private class TimestampColumnSerializer extends ColumnSerializer {
        @Override
        protected void writeObject(ObjectOutput out, Object obj) throws IOException {
            Timestamp ts =  (Timestamp)obj;
            out.writeLong(ts.getTime());
            out.writeInt(ts.getNanos());
        }
        @Override
        protected Object readObject(ObjectInput in) throws IOException {
            Timestamp ts = new Timestamp(in.readLong());
            ts.setNanos(in.readInt());
            return ts;
        }
    }
        
    private ColumnSerializer getSerializer(String type, byte version) {
    	ColumnSerializer cs = null;
    	if (version == 1) {
    		cs = version1serializers.get((type == null) ? DefaultDataTypeManager.DefaultDataTypes.OBJECT : type);
    	}
    	if (cs == null) {
    		cs = serializers.get((type == null) ? DefaultDataTypeManager.DefaultDataTypes.OBJECT : type);
    	}
        if (cs == null) {
        	return defaultSerializer;
        }
        return cs;
    }

    @Override
    public void writeBatch(ObjectOutput out, String[] types, List<? extends List<?>> batch) throws IOException {
    	writeBatch(out, types, batch, (byte)1);
    }
    
    @Override
    public void writeBatch(ObjectOutput out, String[] types, List<? extends List<?>> batch, byte version) throws IOException {
        if (batch == null) {
            out.writeInt(-1);
        } else {
            out.writeInt(batch.size());
            if (batch.size() > 0) {
	            int columns = types.length;
	            out.writeInt(columns);
	            for(int i = 0; i < columns; i++) {
	            	ColumnSerializer serializer = getSerializer(types[i], version);
	                try {
	                    serializer.writeColumn(out, i, batch);
	                } catch (ClassCastException e) {
	                    Object obj = null;
	                    String objectClass = null;
	                    objectSearch: for (int row = 0; row < batch.size(); row++) {
	                        obj = batch.get(row).get(i);
	                        if (obj != null) {
	                            objectClass = obj.getClass().getName();
	                            break objectSearch;
	                        }
	                    }
	                    throw new RuntimeException(Messages.gs(Messages.TEIID.TEIID20001, new Object[] {types[i], new Integer(i), objectClass}), e);
	                }
	            }
            }
        }
    }
    
    @Override
    public List<List<Object>> readBatch(ObjectInput in, String[] types) throws IOException, ClassNotFoundException {
    	return readBatch(in, types, (byte)0);
    }
    
    private List<List<Object>> readBatch(ObjectInput in, String[] types, byte version) throws IOException, ClassNotFoundException {
        int rows = in.readInt();
        if (rows == 0) {
            return new ArrayList<List<Object>>(0);
        } else if (rows > 0) {
            int columns = in.readInt();
            List<List<Object>> batch = new ResizingArrayList<List<Object>>(rows);
            int numBytes = rows/8;
            int extraRows = rows % 8;
            for (int currentRow = 0; currentRow < rows; currentRow++) {
                batch.add(currentRow, Arrays.asList(new Object[columns]));
            }
            byte[] isNullBuffer = new byte[(extraRows > 0) ? numBytes + 1: numBytes];
            for (int col = 0; col < columns; col++) {
                getSerializer(types[col], version).readColumn(in, col, batch, isNullBuffer);
            }
            return batch;
        }
        return null;
    }
}
