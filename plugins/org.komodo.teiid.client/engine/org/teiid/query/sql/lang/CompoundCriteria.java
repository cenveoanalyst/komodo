/* Generated By:JJTree: Do not edit this line. CompoundCriteria.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import java.util.ArrayList;
import java.util.List;

import org.komodo.spi.query.sql.lang.ICompoundCriteria;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.runtime.client.Messages;

/**
 *
 */
public class CompoundCriteria extends Criteria implements ICompoundCriteria<Criteria, TCLanguageVisitorImpl> {

    /** Constant indicating the logical "or" of two or more criteria. */
    public static int OR = 1;

    /** Constant indicating the logical "and" of two or more criteria.*/
    public static int AND = 0;

    /** The criterias. */
    private List<Criteria> criteria;  // List<Criteria>

    /** The logical operator. */
    private int operator = 0;

    /**
     * @param p
     * @param id
     */
    public CompoundCriteria(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * Returns the operator used in the logical expression.  The returned value
     * can be compared to constants defined in this class.
     * @return The operator
     */
    @Override
    public int getOperator() {
        return this.operator;
    }

    /**
     * Return true if the specified operator is a valid operator
     * @param operator Operator to check
     * @return True if valid
     */
    private boolean isValidOperator(int operator) {
        return (operator == OR || operator == AND);
    }

    /**
     * Sets the operator used in the logical expression.
     * @param operator The operator
     * @throws IllegalArgumentException If operator is invalid
     */
    public void setOperator(int operator) {
        if (!isValidOperator(operator)) {
            throw new IllegalArgumentException(Messages.getString(Messages.ERR.ERR_015_010_0002, operator));
        }
        this.operator = operator;
    }

    /**
     * Returns the list of criteria.
     * @return List of {@link Criteria}
     */
    @Override
    public List<Criteria> getCriteria() {
        if (criteria == null)
            criteria = new ArrayList<Criteria>();

        return this.criteria;
    }

    @Override
    public Criteria getCriteria(int index) {
        return this.criteria.get(index);
    }

    /**
     * Returns the number of criteria in this clause.
     * @return Criteria
     */
    @Override
    public int getCriteriaCount() {
        return this.criteria.size();
    }

    @Override
    public void addCriteria(Criteria criteria) {
        this.criteria.add(criteria);
    }

    /**
     * Sets the criteria.
     * @param criteria The list of {@link Criteria}
     */
    public void setCriteria(List<? extends Criteria> criteria) {
        this.criteria = (List<Criteria>) criteria;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.criteria == null) ? 0 : this.criteria.hashCode());
        result = prime * result + this.operator;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        CompoundCriteria other = (CompoundCriteria)obj;
        if (this.criteria == null) {
            if (other.criteria != null) return false;
        } else if (!this.criteria.equals(other.criteria)) return false;
        if (this.operator != other.operator) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    @Override
    public CompoundCriteria clone() {
        CompoundCriteria clone = new CompoundCriteria(this.parser, this.id);

        clone.setOperator(getOperator());
        if(getCriteria() != null)
            clone.setCriteria(cloneList(getCriteria()));

        return clone;
    }

}
/* JavaCC - OriginalChecksum=e9c63d228bd02df17a0d541cee1f0c51 (do not edit this line) */
