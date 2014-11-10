/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.komodo.spi.runtime.version.TeiidVersion;
import org.komodo.spi.runtime.version.DefaultTeiidVersion.Version;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.visitor.SQLStringVisitorImpl;

/**
 * Base class for AST Nodes
 */
public class SimpleNode implements Node, LanguageObject {

    protected Node parent;
    protected Node[] children;
    protected int id;
    protected Object value;
    protected TeiidClientParser parser;

    /**
     * @param p
     * @param i
     */
    public SimpleNode(TeiidClientParser p, int i) {
        id = i;
        parser = p;
    }

    @Override
    public TeiidClientParser getTeiidParser() {
        return parser;
    }

    @Override
    public TeiidVersion getTeiidVersion() {
        return parser.getVersion();
    }

    protected boolean isTeiidVersionOrGreater(Version teiidVersion) {
        TeiidVersion minVersion = getTeiidVersion().getMinimumVersion();
        return minVersion.equals(teiidVersion.get()) || minVersion.isGreaterThan(teiidVersion.get());
    }

    protected boolean isLessThanTeiidVersion(Version teiidVersion) {
        TeiidVersion maxVersion = getTeiidVersion().getMaximumVersion();
        return maxVersion.isLessThan(teiidVersion.get());
    }

    protected boolean isTeiid8OrGreater() {
        return isTeiidVersionOrGreater(Version.TEIID_8_0);
    }

    protected boolean isTeiid87OrGreater() {
        return isTeiidVersionOrGreater(Version.TEIID_8_7);
    }

    @Override
    public void jjtOpen() {
    }

    @Override
    public void jjtClose() {
    }

    @Override
    public void jjtSetParent(Node n) {
        parent = n;
    }

    @Override
    public Node jjtGetParent() {
        return parent;
    }

    @Override
    public void jjtAddChild(Node n, int i) {
        if (children == null) {
            children = new Node[i + 1];
        } else if (i >= children.length) {
            Node c[] = new Node[i + 1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }

    @Override
    public Node jjtGetChild(int i) {
        return children[i];
    }

    @Override
    public int jjtGetNumChildren() {
        return (children == null) ? 0 : children.length;
    }

    /**
     * @param value
     */
    public void jjtSetValue(Object value) {
        this.value = value;
    }

    /**
     * @return value
     */
    public Object jjtGetValue() {
        return value;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    /**
     * Return a String representation of this object using SQLStringVisitor.
     * @return String representation using SQLStringVisitor
     */
    @Override
    public String toString() {
        return SQLStringVisitorImpl.getSQLString(this);
    }

    /* Override this method if you want to customize how the node dumps
       out its children. */

    /**
     * Dump the tree of nodes
     */
    public void dump() {
        System.out.println(toString());
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                SimpleNode n = (SimpleNode)children[i];
                if (n != null) {
                    n.dump();
                }
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SimpleNode other = (SimpleNode)obj;
        if (this.id != other.id) return false;
        return true;
    }

    @Override
    public SimpleNode clone() {
        SimpleNode clone = new SimpleNode(this.parser, this.id);
        return clone;
    }

    protected <T extends LanguageObject> Collection<T> cloneCollection(Collection<T> collection) {
        if (collection == null)
            return null;

        Collection<T> cloned = new HashSet<T>();
        for (T item : collection) {
            cloned.add((T) item.clone());
        }

        return cloned;
    }

    protected <T extends LanguageObject> List<T> cloneList(List<T> list) {
        if (list == null)
            return null;

        List<T> cloned = new ArrayList<T>();
        for (T item : list) {
            cloned.add((T) item.clone());
        }

        return cloned;
    }

//    protected <T extends LanguageObject> T[] cloneArray(T[] array) {
//        if (array == null)
//            return null;
//
//        T[] cloned = (T[]) Array.newInstance(array.getClass(), array.length);
//        for (int i = 0; i < array.length; ++i) {
//            cloned[i] = (T) array[i].clone();
//        }
//        
//        return cloned;
//    }
}

/* JavaCC - OriginalChecksum=3993b98b61510cfa6ed488fcdfd2afcf (do not edit this line) */
