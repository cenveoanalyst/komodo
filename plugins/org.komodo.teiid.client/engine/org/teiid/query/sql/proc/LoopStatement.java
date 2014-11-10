/* Generated By:JJTree: Do not edit this line. LoopStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.proc;

import org.komodo.spi.query.sql.proc.ILoopStatement;
import org.teiid.query.parser.TCLanguageVisitorImpl;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.lang.Command;
import org.teiid.query.sql.lang.Labeled;
import org.teiid.query.sql.lang.Query;
import org.teiid.query.sql.lang.SubqueryContainer;

/**
 *
 */
public class LoopStatement extends Statement
    implements Labeled, SubqueryContainer<Command>, ILoopStatement<TCLanguageVisitorImpl, Command> {

    private String cursorName;

    private Block loopBlock;

    private Command query;

    private String label;

    /**
     * @param p
     * @param id
     */
    public LoopStatement(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * Return the type for this statement, this is one of the types
     * defined on the statement object.
     * @return The statement type
     */
    @Override
    public StatementType getType() {
        return StatementType.TYPE_LOOP;
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
     * @return cursor name
     */
    public String getCursorName() {
        return cursorName;
    }

    /**
     * @param cursorName
     */
    public void setCursorName(String cursorName) {
        this.cursorName = cursorName;
    }

    /**
     * @return block
     */
    public Block getBlock() {
        return loopBlock;
    }

    /**
     * @param block
     */
    public void setBlock(Block block) {
        loopBlock = block;
    }

    /**
     * @return command
     */
    @Override
    public Command getCommand() {
        return query;
    }

    /**
     * Sets the command. 
     */
    @Override
    public void setCommand(Command command){
        this.query = command;
    }

    /**
     * @param query
     */
    public void setCommand(Query query) {
        this.query = query;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.cursorName == null) ? 0 : this.cursorName.hashCode());
        result = prime * result + ((this.label == null) ? 0 : this.label.hashCode());
        result = prime * result + ((this.loopBlock == null) ? 0 : this.loopBlock.hashCode());
        result = prime * result + ((this.query == null) ? 0 : this.query.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        LoopStatement other = (LoopStatement)obj;
        if (this.cursorName == null) {
            if (other.cursorName != null) return false;
        } else if (!this.cursorName.equals(other.cursorName)) return false;
        if (this.label == null) {
            if (other.label != null) return false;
        } else if (!this.label.equals(other.label)) return false;
        if (this.loopBlock == null) {
            if (other.loopBlock != null) return false;
        } else if (!this.loopBlock.equals(other.loopBlock)) return false;
        if (this.query == null) {
            if (other.query != null) return false;
        } else if (!this.query.equals(other.query)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(TCLanguageVisitorImpl visitor) {
        visitor.visit(this);
    }

    @Override
    public LoopStatement clone() {
        LoopStatement clone = new LoopStatement(this.parser, this.id);

        if(getLabel() != null)
            clone.setLabel(getLabel());
        if(getCommand() != null)
            clone.setCommand(getCommand().clone());
        if(getBlock() != null)
            clone.setBlock(getBlock().clone());
        if(getCursorName() != null)
            clone.setCursorName(getCursorName());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=fe4519dc09735b802146c0bcd4db1308 (do not edit this line) */
