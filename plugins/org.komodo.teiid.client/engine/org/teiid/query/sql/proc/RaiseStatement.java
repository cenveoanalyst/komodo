/* Generated By:JJTree: Do not edit this line. RaiseStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.proc;

import org.komodo.spi.annotation.Since;
import org.komodo.spi.query.sql.proc.IRaiseStatement;
import org.komodo.spi.runtime.version.DefaultTeiidVersion.Version;
import org.teiid.core.types.DefaultDataTypeManager;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.symbol.Expression;

/**
 *
 */
@Since(Version.TEIID_8_0)
public class RaiseStatement extends Statement
    implements ExpressionStatement, IRaiseStatement<TCLanguageVisitorImpl, Expression> {

    private Expression expression;

    private boolean warning;

    /**
     * @param p
     * @param id
     */
    public RaiseStatement(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * Return the type for this statement, this is one of the types
     * defined on the statement object.
     * @return The statement type
     */
    @Override
    public StatementType getType() {
        return StatementType.TYPE_ERROR;
    }

    /**
     * @return the expression
     */
    @Override
    public Expression getExpression() {
        return this.expression;
    }

    /**
     * @param expression the expression to set
     */
    @Override
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * @return the warning
     */
    public boolean isWarning() {
        return this.warning;
    }

    /**
     * @param warning the warning to set
     */
    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    @Override
    public Class<?> getExpectedType() {
        return DefaultDataTypeManager.DefaultDataTypes.OBJECT.getTypeClass();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.expression == null) ? 0 : this.expression.hashCode());
        result = prime * result + (this.warning ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        RaiseStatement other = (RaiseStatement)obj;
        if (this.expression == null) {
            if (other.expression != null) return false;
        } else if (!this.expression.equals(other.expression)) return false;
        if (this.warning != other.warning) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    @Override
    public RaiseStatement clone() {
        RaiseStatement clone = new RaiseStatement(this.parser, this.id);

        if(getExpression() != null)
            clone.setExpression(getExpression().clone());
        clone.setWarning(isWarning());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=8a2fd49e702012817f644e1725684e48 (do not edit this line) */
