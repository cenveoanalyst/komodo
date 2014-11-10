/* Generated By:JJTree: Do not edit this line. Block.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.proc;

import java.util.ArrayList;
import java.util.List;

import org.komodo.spi.query.sql.SQLConstants;
import org.komodo.spi.query.sql.proc.IBlock;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidNodeFactory.ASTNodes;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.lang.Command;
import org.teiid.query.sql.lang.Labeled;
import org.teiid.query.sql.symbol.ElementSymbol;
import org.teiid.query.sql.symbol.Symbol;

/**
 *
 */
public class Block extends Statement implements Labeled, IBlock<Statement, TCLanguageVisitorImpl> {

    private List<Statement> statements = new ArrayList<Statement>();

    private boolean atomic;

    private String label;

    private String exceptionGroup;

    private List<Statement> exceptionStatements;

    /**
     * @param p
     * @param id
     */
    public Block(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * Return the type for this statement, this is one of the types
     * defined on the statement object.
     * @return The statement type
     */
    @Override
    public StatementType getType() {
        return StatementType.TYPE_COMPOUND;
    }

    /**
     * @return the statements
     */
    @Override
    public List<Statement> getStatements() {
        return this.statements;
    }

    /**
     * Add a <code>Statement</code> to this block.
     * @param statement The <code>Statement</code> to be added to the block
     */
    @Override
    public void addStatement(Statement statement) {
        addStatement(statement, false);
    }

    /**
     * @param statement
     * @param exception
     */
    @SuppressWarnings( "deprecation" )
    public void addStatement(Statement statement, boolean exception) {
        if (statement instanceof AssignmentStatement) {
            AssignmentStatement stmt = (AssignmentStatement)statement;
            Command cmd = stmt.getCommand();
            if (cmd != null) {
                CommandStatement cs = parser.createASTNode(ASTNodes.COMMAND_STATEMENT);
                cs.setCommand(cmd);
                internalAddStatement(cs, exception);
                stmt.setCommand(null);
                stmt.setExpression(null);
                ElementSymbol variable = stmt.getVariable();
                if (variable != null && variable.getShortName().equalsIgnoreCase(SQLConstants.ROWCOUNT) 
                        && variable.getGroupSymbol() != null && variable.getGroupSymbol().getName().equalsIgnoreCase(SQLConstants.VARIABLES)) {
                    return;
                }
                String fullName = SQLConstants.VARIABLES + Symbol.SEPARATOR + SQLConstants.ROWCOUNT;
                ElementSymbol es = parser.createASTNode(ASTNodes.ELEMENT_SYMBOL);
                es.setName(fullName);
                stmt.setExpression(es);
            }
        }
        internalAddStatement(statement, exception);
    }

    private void internalAddStatement(Statement statement, boolean exception) {
        if (exception) {
            if (this.exceptionStatements == null) {
                exceptionStatements = new ArrayList<Statement>();
            }
            exceptionStatements.add(statement);
        } else {
            statements.add(statement);
        }
    }

    /**
     * @param statements the statements to set
     */
    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    /**
     * @return the atomic
     */
    public boolean isAtomic() {
        return atomic;
    }

    /**
     * @param atomic the atomic to set
     */
    public void setAtomic(boolean atomic) {
        this.atomic = atomic;
    }

    @Override
    public String getLabel() {
        return label;
    }
    
    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the exceptionGroup
     */
    public String getExceptionGroup() {
        return exceptionGroup;
    }

    /**
     * @param exceptionGroup the exceptionGroup to set
     */
    public void setExceptionGroup(String exceptionGroup) {
        this.exceptionGroup = exceptionGroup;
    }

    /**
     * @return exception statements
     */
    public List<Statement> getExceptionStatements() {
        return exceptionStatements;
    }

    /**
     * @param exceptionStatements
     */
    public void setExceptionStatements(List<Statement> exceptionStatements) {
        this.exceptionStatements = exceptionStatements;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.atomic ? 1231 : 1237);
        result = prime * result + ((this.exceptionGroup == null) ? 0 : this.exceptionGroup.hashCode());
        result = prime * result + ((this.exceptionStatements == null) ? 0 : this.exceptionStatements.hashCode());
        result = prime * result + ((this.label == null) ? 0 : this.label.hashCode());
        result = prime * result + ((this.statements == null) ? 0 : this.statements.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Block other = (Block)obj;
        if (this.atomic != other.atomic) return false;
        if (this.exceptionGroup == null) {
            if (other.exceptionGroup != null) return false;
        } else if (!this.exceptionGroup.equals(other.exceptionGroup)) return false;
        if (this.exceptionStatements == null) {
            if (other.exceptionStatements != null) return false;
        } else if (!this.exceptionStatements.equals(other.exceptionStatements)) return false;
        if (this.label == null) {
            if (other.label != null) return false;
        } else if (!this.label.equals(other.label)) return false;
        if (this.statements == null) {
            if (other.statements != null) return false;
        } else if (!this.statements.equals(other.statements)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    @Override
    public Block clone() {
        Block clone = new Block(this.parser, this.id);

        clone.setAtomic(isAtomic());
        if(getLabel() != null)
            clone.setLabel(getLabel());
        if(getExceptionGroup() != null)
            clone.setExceptionGroup(getExceptionGroup());
        if(getExceptionStatements() != null)
            clone.setExceptionStatements(cloneList(getExceptionStatements()));
        if(getStatements() != null)
            clone.setStatements(cloneList(getStatements()));

        return clone;
    }

}
/* JavaCC - OriginalChecksum=c90f4508d390bd046d7adfa163192b9f (do not edit this line) */
