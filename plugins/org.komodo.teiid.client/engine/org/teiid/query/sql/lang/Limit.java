/* Generated By:JJTree: Do not edit this line. Limit.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import org.komodo.spi.query.sql.lang.ILimit;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.symbol.Expression;

/**
 *
 */
public class Limit extends SimpleNode implements ILimit<TCLanguageVisitorImpl> {

    /**
     * Non Strict token
     */
    public static String NON_STRICT = "NON_STRICT"; //$NON-NLS-1$

    private Expression offset;

    private Expression rowLimit;

    private boolean implicit;

    private boolean strict = true;

    /**
     * @param p
     * @param id
     */
    public Limit(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * @param strict
     */
    public void setStrict(boolean strict) {
        this.strict = strict;
    }
    
    /**
     * @return strict
     */
    public boolean isStrict() {
        return strict;
    }
    
    /**
     * @return implicit
     */
    public boolean isImplicit() {
        return implicit;
    }
    
    /**
     * @param implicit
     */
    public void setImplicit(boolean implicit) {
        this.implicit = implicit;
    }
    
    /**
     * @return offset
     */
    public Expression getOffset() {
        return offset;
    }
    
    /**
     * @param offset
     */
    public void setOffset(Expression offset) {
        this.offset = offset;
    }
    
    /**
     * @return row limit
     */
    public Expression getRowLimit() {
        return rowLimit;
    }
    
    /**
     * @param rowLimit
     */
    public void setRowLimit(Expression rowLimit ) {
        this.rowLimit = rowLimit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.implicit ? 1231 : 1237);
        result = prime * result + ((this.offset == null) ? 0 : this.offset.hashCode());
        result = prime * result + ((this.rowLimit == null) ? 0 : this.rowLimit.hashCode());
        result = prime * result + (this.strict ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Limit other = (Limit)obj;
        if (this.implicit != other.implicit) return false;
        if (this.offset == null) {
            if (other.offset != null) return false;
        } else if (!this.offset.equals(other.offset)) return false;
        if (this.rowLimit == null) {
            if (other.rowLimit != null) return false;
        } else if (!this.rowLimit.equals(other.rowLimit)) return false;
        if (this.strict != other.strict) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    @Override
    public Limit clone() {
        Limit clone = new Limit(this.parser, this.id);

        clone.setStrict(isStrict());
        clone.setImplicit(isImplicit());
        if(getRowLimit() != null)
            clone.setRowLimit(getRowLimit().clone());
        if(getOffset() != null)
            clone.setOffset(getOffset().clone());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=ac356bcb126e51b23a771bf5e2b89dfc (do not edit this line) */
