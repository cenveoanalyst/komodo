/* Generated By:JJTree: Do not edit this line. XMLTable.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import java.util.ArrayList;
import java.util.List;

import org.komodo.spi.query.sql.lang.IXMLTable;
import org.komodo.spi.runtime.version.DefaultTeiidVersion.Version;
import org.teiid.core.types.DefaultDataTypeManager;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidNodeFactory.ASTNodes;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.symbol.DerivedColumn;
import org.teiid.query.sql.symbol.XMLNamespaces;
import org.teiid.query.xquery.saxon.SaxonXQueryExpression;

/**
 *
 */
public class XMLTable extends TableFunctionReference implements IXMLTable<TCLanguageVisitorImpl> {

    private List<XMLColumn> columns = new ArrayList<XMLColumn>();

    private String xquery;

    private List<DerivedColumn> passing = new ArrayList<DerivedColumn>();

    private boolean usingDefaultColumn;

    private XMLNamespaces namespaces;

    private SaxonXQueryExpression xqueryExpression;

    /**
     * @param p
     * @param id
     */
    public XMLTable(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * @return passing
     */
    public List<DerivedColumn> getPassing() {
        return passing;
    }

    /**
     * @param passing
     */
    public void setPassing(List<DerivedColumn> passing) {
        this.passing = passing;
    }

    /**
     * @return xquery
     */
    public String getXquery() {
        return xquery;
    }
    
    /**
     * @param xquery
     */
    public void setXquery(String xquery) {
        this.xquery = xquery;
    }

    /**
     * @return using default column
     */
    public boolean isUsingDefaultColumn() {
        return usingDefaultColumn;
    }

    /**
     * @param usingDefaultColumn the usingDefaultColumn to set
     */
    public void setUsingDefaultColumn(boolean usingDefaultColumn) {
        this.usingDefaultColumn = usingDefaultColumn;
    }

    /**
     * @return columns
     */
    public List<XMLColumn> getColumns() {
        return columns;
    }
    
    /**
     * @param columns
     */
    public void setColumns(List<XMLColumn> columns) {
        if (getTeiidVersion().isGreaterThanOrEqualTo(Version.TEIID_8_0.get()) && columns.isEmpty()) {
            usingDefaultColumn = true;
            XMLColumn xmlColumn = parser.createASTNode(ASTNodes.XML_COLUMN);
            xmlColumn.setName("OBJECT_VALUE"); //$NON-NLS-1$
            xmlColumn.setType(DefaultDataTypeManager.DefaultDataTypes.XML.getId());
            xmlColumn.setPath("."); //$NON-NLS-1$
            columns.add(xmlColumn);
        }
        this.columns = columns;
    }

    /**
     * @return namespaces
     */
    public XMLNamespaces getNamespaces() {
        return namespaces;
    }
    
    /**
     * @param namespaces
     */
    public void setNamespaces(XMLNamespaces namespaces) {
        this.namespaces = namespaces;
    }

    /**
     * @throws Exception
     */
    public void compileXqueryExpression() throws Exception {
        this.xqueryExpression = new SaxonXQueryExpression(xquery, namespaces, passing, this.columns);
    }
    
    /**
     * @return saxon xquery expression
     */
    public SaxonXQueryExpression getXQueryExpression() {
        return xqueryExpression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.columns == null) ? 0 : this.columns.hashCode());
        result = prime * result + ((this.namespaces == null) ? 0 : this.namespaces.hashCode());
        result = prime * result + ((this.passing == null) ? 0 : this.passing.hashCode());
        result = prime * result + (this.usingDefaultColumn ? 1231 : 1237);
        result = prime * result + ((this.xquery == null) ? 0 : this.xquery.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        XMLTable other = (XMLTable)obj;
        if (this.columns == null) {
            if (other.columns != null)
                return false;
        } else if (!this.columns.equals(other.columns))
            return false;
        if (this.namespaces == null) {
            if (other.namespaces != null)
                return false;
        } else if (!this.namespaces.equals(other.namespaces))
            return false;
        if (this.passing == null) {
            if (other.passing != null)
                return false;
        } else if (!this.passing.equals(other.passing))
            return false;
        if (this.usingDefaultColumn != other.usingDefaultColumn)
            return false;
        if (this.xquery == null) {
            if (other.xquery != null)
                return false;
        } else if (!this.xquery.equals(other.xquery))
            return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    @Override
    public XMLTable clone() {
        XMLTable clone = new XMLTable(this.parser, this.id);

        if(getColumns() != null)
            clone.setColumns(cloneList(getColumns()));
        if(getPassing() != null)
            clone.setPassing(cloneList(getPassing()));
        if(getNamespaces() != null)
            clone.setNamespaces(getNamespaces().clone());
        if(getXquery() != null)
            clone.setXquery(getXquery());
        clone.setUsingDefaultColumn(isUsingDefaultColumn());
        if(getName() != null)
            clone.setName(getName());
        clone.setOptional(isOptional());
        clone.setMakeInd(isMakeInd());
        clone.setNoUnnest(isNoUnnest());
        clone.setMakeDep(isMakeDep());
        clone.setMakeNotDep(isMakeNotDep());
        clone.setPreserve(isPreserve());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=7e5530ab340020c0908e2b5c8b4448f3 (do not edit this line) */
