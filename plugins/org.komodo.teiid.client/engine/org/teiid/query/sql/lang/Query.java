/* Generated By:JJTree: Do not edit this line. Query.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.komodo.spi.annotation.Since;
import org.komodo.spi.query.sql.lang.IQuery;
import org.komodo.spi.runtime.version.DefaultTeiidVersion.Version;
import org.teiid.core.types.DefaultDataTypeManager;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidNodeFactory.ASTNodes;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.symbol.ElementSymbol;
import org.teiid.query.sql.symbol.Expression;
import org.teiid.query.sql.visitor.AggregateSymbolCollectorVisitorImpl;

/**
 *
 */
public class Query extends QueryCommand
    implements IQuery<Select, From, Into, Criteria, GroupBy, OrderBy, Query, Expression, TCLanguageVisitorImpl> {

    /** The select clause. */
    private Select select;

    /** The from clause. */
    private From from;

    /** The criteria specifying constraints on what data will be retrieved. */
    private Criteria criteria;

    /** The group by specifying how to group rows. */
    private GroupBy groupBy;

    /** The having specifying which group rows will be returned. */
    private Criteria having;

    /** XML flag */
    private boolean isXML;

    /** The into clause. */
    private Into into;

    /** xml projected symbols */
    private List<Expression> selectList;

    /*
     * Added in Teiid 8.6 but backward-compatible with all
     * Teiid 8+ parsers.
     */
    @Since(Version.TEIID_8_6)
    private boolean isRowConstructor;
    
    /**
     * @param p
     * @param id
     */
    public Query(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * Return type of command.
     * @return TYPE_QUERY
     */
    @Override
    public int getType() {
        return TYPE_QUERY;
    }

    /**
     * @return the select
     */
    @Override
    public Select getSelect() {
        return this.select;
    }

    /**
     * @param select the select to set
     */
    @Override
    public void setSelect(Select select) {
        this.select = select;
    }

    /**
     * @return the from
     */
    @Override
    public From getFrom() {
        return this.from;
    }

    /**
     * @param from the from to set
     */
    @Override
    public void setFrom(From from) {
        this.from = from;
    }

    /**
     * @return the criteria
     */
    @Override
    public Criteria getCriteria() {
        return this.criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    @Override
    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    /**
     * @return the groupBy
     */
    @Override
    public GroupBy getGroupBy() {
        return this.groupBy;
    }

    /**
     * @param groupBy the groupBy to set
     */
    @Override
    public void setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    /**
     * @return the having
     */
    @Override
    public Criteria getHaving() {
        return this.having;
    }

    /**
     * @param having the having to set
     */
    @Override
    public void setHaving(Criteria having) {
        this.having = having;
    }

    /**
     * @return the isXML
     */
    public boolean isXML() {
        return this.isXML;
    }

    /**
     * @param isXML the isXML to set
     */
    public void setXML(boolean isXML) {
        this.isXML = isXML;
    }

    /**
     * @return the into
     */
    @Override
    public Into getInto() {
        return this.into;
    }

    /**
     * @param into the into to set
     */
    @Override
    public void setInto(Into into) {
        this.into = into;
    }

    /**
     * @return the selectList
     */
    public List<Expression> getSelectList() {
        return this.selectList;
    }

    /**
     * @param selectList the selectList to set
     */
    public void setSelectList(List<Expression> selectList) {
        this.selectList = selectList;
    }

    @Override
    public boolean returnsResultSet() {
        return into == null;
    }

    /**
     * Get the xml flag for the query
     * @return boolean
     */
    public boolean getIsXML() {
        return isXML;
    }
    
    /**
     * Set the xml flag for the query
     *
     * @param isXML
     */
    public void setIsXML(boolean isXML) {
        this.isXML = isXML;
    }

    /**
     * @return row constructor flag
     */
    public boolean isRowConstructor() {
        if (isLessThanTeiidVersion(Version.TEIID_8_0))
            return false;

        return isRowConstructor;
    }
    
    /**
     * @param isRowConstructor
     */
    public void setRowConstructor(boolean isRowConstructor) {
        if (isLessThanTeiidVersion(Version.TEIID_8_0))
            return;

        this.isRowConstructor = isRowConstructor;
    }

    /**
     * Get the ordered list of all elements returned by this query.  These elements
     * may be ElementSymbols or ExpressionSymbols but in all cases each represents a 
     * single column.
     * @return Ordered list of SingleElementSymbol
     */
    @Override
    public List<Expression> getProjectedSymbols() {
        if (!getIsXML()) {
            if(getSelect() != null) { 
                if(getInto() != null){
                    //SELECT INTO clause
                    return getUpdateCommandSymbol();
                }
                return getSelect().getProjectedSymbols();
            }
            return Collections.emptyList();
        }
        if(selectList == null){
            selectList = new ArrayList<Expression>(1);
            ElementSymbol xmlElement = parser.createASTNode(ASTNodes.ELEMENT_SYMBOL);
            xmlElement.setName("xml"); //$NON-NLS-1$
            xmlElement.setType(DefaultDataTypeManager.DefaultDataTypes.XML.getTypeClass());
            selectList.add(xmlElement);
        }
        return selectList;
    }

    @Override
    public Query getProjectedQuery() {
        return this;
    }

    public boolean hasAggregates() {
        return getGroupBy() != null 
        || getHaving() != null 
        || !AggregateSymbolCollectorVisitorImpl.getAggregates(getSelect(), false).isEmpty();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.criteria == null) ? 0 : this.criteria.hashCode());
        result = prime * result + ((this.from == null) ? 0 : this.from.hashCode());
        result = prime * result + ((this.groupBy == null) ? 0 : this.groupBy.hashCode());
        result = prime * result + ((this.having == null) ? 0 : this.having.hashCode());
        result = prime * result + ((this.into == null) ? 0 : this.into.hashCode());
        result = prime * result + (this.isXML ? 1231 : 1237);
        result = prime * result + ((this.select == null) ? 0 : this.select.hashCode());
        result = prime * result + ((this.selectList == null) ? 0 : this.selectList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Query other = (Query)obj;
        if (this.criteria == null) {
            if (other.criteria != null) return false;
        } else if (!this.criteria.equals(other.criteria)) return false;
        if (this.from == null) {
            if (other.from != null) return false;
        } else if (!this.from.equals(other.from)) return false;
        if (this.groupBy == null) {
            if (other.groupBy != null) return false;
        } else if (!this.groupBy.equals(other.groupBy)) return false;
        if (this.having == null) {
            if (other.having != null) return false;
        } else if (!this.having.equals(other.having)) return false;
        if (this.into == null) {
            if (other.into != null) return false;
        } else if (!this.into.equals(other.into)) return false;
        if (this.isXML != other.isXML) return false;
        if (this.select == null) {
            if (other.select != null) return false;
        } else if (!this.select.equals(other.select)) return false;
        if (this.selectList == null) {
            if (other.selectList != null) return false;
        } else if (!this.selectList.equals(other.selectList)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    @Override
    public Query clone() {
        Query clone = new Query(this.parser, this.id);

        if(getCriteria() != null)
            clone.setCriteria(getCriteria().clone());
        if(getSelect() != null)
            clone.setSelect(getSelect().clone());
        if(getFrom() != null)
            clone.setFrom(getFrom().clone());
        if(getGroupBy() != null)
            clone.setGroupBy(getGroupBy().clone());
        if(getHaving() != null)
            clone.setHaving(getHaving().clone());
        clone.setXML(isXML());
        if(getInto() != null)
            clone.setInto(getInto().clone());
        if(getSelectList() != null)
            clone.setSelectList(cloneList(getSelectList()));
        if(getOrderBy() != null)
            clone.setOrderBy(getOrderBy().clone());
        if(getLimit() != null)
            clone.setLimit(getLimit().clone());
        if(getWith() != null)
            clone.setWith(cloneList(getWith()));
        if(getSourceHint() != null)
            clone.setSourceHint(getSourceHint());
        if(getOption() != null)
            clone.setOption(getOption().clone());

        clone.setRowConstructor(isRowConstructor());

        copyMetadataState(clone);

        return clone;
    }

}
/* JavaCC - OriginalChecksum=5836b6b4cdb7e7d9afd6ee599f469169 (do not edit this line) */
