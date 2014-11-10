/* Generated By:JJTree: Do not edit this line. SetClause.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import org.komodo.spi.query.sql.lang.ISetClause;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.symbol.ElementSymbol;
import org.teiid.query.sql.symbol.Expression;

/**
 *
 */
public class SetClause extends SimpleNode implements ISetClause<TCLanguageVisitorImpl> {

    private ElementSymbol symbol;

    private Expression value;

    /**
     * @param p
     * @param id
     */
    public SetClause(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * @return symbol
     */
    public ElementSymbol getSymbol() {
        return symbol;
    }

    /**
     * @param symbol
     */
    public void setSymbol(ElementSymbol symbol) {
        this.symbol = symbol;
    }

    /**
     * @return value
     */
    public Expression getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(Expression value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.symbol == null) ? 0 : this.symbol.hashCode());
        result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        SetClause other = (SetClause)obj;
        if (this.symbol == null) {
            if (other.symbol != null) return false;
        } else if (!this.symbol.equals(other.symbol)) return false;
        if (this.value == null) {
            if (other.value != null) return false;
        } else if (!this.value.equals(other.value)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    @Override
    public SetClause clone() {
        SetClause clone = new SetClause(this.parser, this.id);

        if(getSymbol() != null)
            clone.setSymbol(getSymbol().clone());
        if(getValue() != null)
            clone.setValue(getValue().clone());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=79e2fad18b6bbeddc71b298c5fd54e9a (do not edit this line) */
