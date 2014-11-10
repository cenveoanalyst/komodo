/* Generated By:JJTree: Do not edit this line. AggregateSymbol.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.symbol.v7;

import java.util.HashMap;
import java.util.Map;

import org.komodo.spi.annotation.Since;
import org.komodo.spi.runtime.version.DefaultTeiidVersion.Version;
import org.teiid.core.types.DefaultDataTypeManager;
import org.teiid.query.function.TCFunctionDescriptor;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.v7.Teiid7ClientParser;
import org.teiid.query.sql.lang.OrderBy;
import org.teiid.query.sql.symbol.AggregateSymbol;
import org.teiid.query.sql.symbol.Expression;
import org.teiid.query.sql.symbol.ExpressionSymbol;

/**
 * From Teiid Version 7, the AggregateSymbol extends ExpressionSymbol.
 * This is changed in version 8.
 */
public class Aggregate7Symbol extends ExpressionSymbol implements AggregateSymbol {

    private boolean distinct;

    private OrderBy orderBy;

    private Expression condition;

    private Type aggregate;

    private boolean isWindowed;

    private static final Class<?> COUNT_TYPE = DefaultDataTypeManager.DefaultDataTypes.INTEGER.getTypeClass();
    private static final Map<Class<?>, Class<?>> SUM_TYPES;
    private static final Map<Class<?>, Class<?>> AVG_TYPES;

    static {
        Class<?> byteClass = DefaultDataTypeManager.DefaultDataTypes.BYTE.getTypeClass();
        Class<?> longClass = DefaultDataTypeManager.DefaultDataTypes.LONG.getTypeClass();
        Class<?> shortClass = DefaultDataTypeManager.DefaultDataTypes.SHORT.getTypeClass();
        Class<?> integerClass = DefaultDataTypeManager.DefaultDataTypes.INTEGER.getTypeClass();
        Class<?> doubleClass = DefaultDataTypeManager.DefaultDataTypes.DOUBLE.getTypeClass();
        Class<?> bigDecimalClass = DefaultDataTypeManager.DefaultDataTypes.BIG_DECIMAL.getTypeClass();
        Class<?> bigIntegerClass = DefaultDataTypeManager.DefaultDataTypes.BIG_INTEGER.getTypeClass();
        Class<?> floatClass = DefaultDataTypeManager.DefaultDataTypes.FLOAT.getTypeClass();

        SUM_TYPES = new HashMap<Class<?>, Class<?>>();        
        SUM_TYPES.put(byteClass, longClass);
        SUM_TYPES.put(shortClass, longClass);
        SUM_TYPES.put(integerClass, longClass);
        SUM_TYPES.put(longClass, longClass);        
        SUM_TYPES.put(bigIntegerClass, bigIntegerClass);
        SUM_TYPES.put(floatClass, doubleClass);
        SUM_TYPES.put(doubleClass, doubleClass);        
        SUM_TYPES.put(bigDecimalClass, bigDecimalClass);

        AVG_TYPES = new HashMap<Class<?>, Class<?>>();
        AVG_TYPES.put(byteClass, doubleClass);
        AVG_TYPES.put(shortClass, doubleClass);
        AVG_TYPES.put(integerClass, doubleClass);
        AVG_TYPES.put(longClass, doubleClass);
        AVG_TYPES.put(bigIntegerClass, bigDecimalClass);
        AVG_TYPES.put(floatClass, doubleClass);
        AVG_TYPES.put(doubleClass, doubleClass);
        AVG_TYPES.put(bigDecimalClass, bigDecimalClass);        
    }

    /**
     * @param p
     * @param id
     */
    public Aggregate7Symbol(Teiid7ClientParser p, int id) {
        super(p, id);
    }

    private boolean isAnalytical() {
        switch (this.aggregate) {
        case RANK:
        case ROW_NUMBER:
        case DENSE_RANK:
            return true;
        default:
            break;
        }

        return false;
    }

    @Override
    public boolean isBoolean() {
        return this.aggregate == Type.EVERY 
                || this.aggregate == Type.SOME 
                || this.aggregate == Type.ANY;
    }
    
    @Override
    public boolean isEnhancedNumeric() {
        return this.aggregate == Type.STDDEV_POP 
        || this.aggregate == Type.STDDEV_SAMP
        || this.aggregate == Type.VAR_SAMP
        || this.aggregate == Type.VAR_POP;
    }

    /**
     * Get the type of the symbol, which depends on the aggregate function and the
     * type of the contained expression
     * @return Type of the symbol
     */
    @Override
    public Class<?> getType() {
        switch (this.aggregate) {
        case COUNT:
            return COUNT_TYPE;
        case SUM:
            Class<?> expressionType = this.getExpression().getType();
            return SUM_TYPES.get(expressionType);
        case AVG:
            expressionType = this.getExpression().getType();
            return AVG_TYPES.get(expressionType);
        case ARRAY_AGG:
            return DefaultDataTypeManager.DefaultDataTypes.OBJECT.getTypeClass();
        case TEXTAGG:
            return DefaultDataTypeManager.DefaultDataTypes.BLOB.getTypeClass();
        default:
            break;
        }

        if (isBoolean()) {
            return DefaultDataTypeManager.DefaultDataTypes.BOOLEAN.getTypeClass();
        }

        if (isEnhancedNumeric()) {
            return DefaultDataTypeManager.DefaultDataTypes.DOUBLE.getTypeClass();
        }

        if (isAnalytical()) {
            return DefaultDataTypeManager.DefaultDataTypes.INTEGER.getTypeClass();
        }

        return this.getExpression().getType();
    }

    @Override
    public void setAggregateFunction(String aggregateFunction) {
        setAggregateFunction(Type.valueOf(aggregateFunction));
    }

    @Override
    public void setAggregateFunction(Type aggregateFunction) {
        this.aggregate = aggregateFunction;
    }

    /**
     * Get the aggregate function type - this will map to one of the reserved words
     * for the aggregate functions.
     * @return Aggregate function type
     */
    @Override
    public Type getAggregateFunction() {
        return this.aggregate;
    }

    /**
     * Get the distinct flag.  If true, aggregate symbol will remove duplicates during
     * computation.
     * @return True if duplicates should be removed during computation
     */
    @Override
    public boolean isDistinct() {
        return this.distinct;
    }

    @Override
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public OrderBy getOrderBy() {
        return orderBy;
    }

    @Override
    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public Expression getCondition() {
        return condition;
    }
    
    @Override
    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    @Override
    public boolean isWindowed() {
        return isWindowed;
    }

    @Override
    public void setWindowed(boolean isWindowed) {
        this.isWindowed = isWindowed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.aggregate == null) ? 0 : this.aggregate.hashCode());
        result = prime * result + ((this.condition == null) ? 0 : this.condition.hashCode());
        result = prime * result + (this.distinct ? 1231 : 1237);
        result = prime * result + (this.isWindowed ? 1231 : 1237);
        result = prime * result + ((this.orderBy == null) ? 0 : this.orderBy.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Aggregate7Symbol other = (Aggregate7Symbol)obj;
        if (this.aggregate != other.aggregate) return false;
        if (this.condition == null) {
            if (other.condition != null) return false;
        } else if (!this.condition.equals(other.condition)) return false;
        if (this.distinct != other.distinct) return false;
        if (this.isWindowed != other.isWindowed) return false;
        if (this.orderBy == null) {
            if (other.orderBy != null) return false;
        } else if (!this.orderBy.equals(other.orderBy)) return false;
        return true;
    }

    @Override
    public String getCanonicalName() {
        return getShortCanonicalName();
    }

    @Override
    public void setCanonicalName(String canonicalName) {
        setShortCanonicalName(canonicalName);
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit((AggregateSymbol) this);
    }

    @Override
    public Aggregate7Symbol clone() {
        Aggregate7Symbol clone = new Aggregate7Symbol((Teiid7ClientParser) this.parser, this.id);

        if(getAggregateFunction() != null)
            clone.setAggregateFunction(getAggregateFunction());
        if(getAggregateFunction() != null)
            clone.setAggregateFunction(getAggregateFunction());
        clone.setDistinct(isDistinct());
        if(getOrderBy() != null)
            clone.setOrderBy(getOrderBy().clone());
        if(getCondition() != null)
            clone.setCondition(getCondition().clone());
        clone.setWindowed(isWindowed());
        if(getCanonicalName() != null)
            clone.setCanonicalName(getCanonicalName());
        if(getExpression() != null)
            clone.setExpression(getExpression().clone());
        if(getShortCanonicalName() != null)
            clone.setShortCanonicalName(getShortCanonicalName());
        if(getOutputName() != null)
            clone.setOutputName(getOutputName());
        if(getShortName() != null)
            clone.setShortName(getShortName());
        if(getName() != null)
            clone.setName(getName());

        return clone;
    }

    @Override
    @Since(Version.TEIID_8_0)
    public Expression[] getArgs() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Since(Version.TEIID_8_0)
    public Expression getArg(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Since(Version.TEIID_8_0)
    public void setArgs(Expression[] arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Since(Version.TEIID_8_0)
    public void setType(Class<?> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Since(Version.TEIID_8_0)
    public TCFunctionDescriptor getFunctionDescriptor() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Since(Version.TEIID_8_0)
    public void setFunctionDescriptor(TCFunctionDescriptor functionDescriptor) {
        throw new UnsupportedOperationException();
    }
}
/* JavaCC - OriginalChecksum=7efdc98eb15b01c236003d9dc5fca445 (do not edit this line) */
