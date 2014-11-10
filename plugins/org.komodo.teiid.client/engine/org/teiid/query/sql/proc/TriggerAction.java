/* Generated By:JJTree: Do not edit this line. TriggerAction.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.proc;

import java.util.List;

import org.komodo.spi.query.sql.proc.ITriggerAction;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.lang.Command;
import org.teiid.query.sql.symbol.Expression;
import org.teiid.query.sql.symbol.GroupSymbol;

/**
 *
 */
public class TriggerAction extends Command implements ITriggerAction<Expression, TCLanguageVisitorImpl> {

    private Block block;

    private GroupSymbol view;

    /**
     * @param p
     * @param id
     */
    public TriggerAction(TeiidClientParser p, int id) {
        super(p, id);
    }

    @Override
    public int getType() {
        return TYPE_TRIGGER_ACTION;
    }

    /**
     * @return the block
     */
    public Block getBlock() {
        return block;
    }

    /**
     * @param block the block to set
     */
    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public List<Expression> getProjectedSymbols() {
        return getUpdateCommandSymbol();
    }

    /**
     * @return view
     */
    public GroupSymbol getView() {
        return view;
    }
    
    /**
     * @param view
     */
    public void setView(GroupSymbol view) {
        this.view = view;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.block == null) ? 0 : this.block.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        TriggerAction other = (TriggerAction)obj;
        if (this.block == null) {
            if (other.block != null) return false;
        } else if (!this.block.equals(other.block)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    @Override
    public TriggerAction clone() {
        TriggerAction clone = new TriggerAction(this.parser, this.id);

        if(getBlock() != null)
            clone.setBlock(getBlock().clone());
        if(getSourceHint() != null)
            clone.setSourceHint(getSourceHint());
        if(getOption() != null)
            clone.setOption(getOption().clone());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=a7f2d6ba04e5449efe5634e804bbd15b (do not edit this line) */
