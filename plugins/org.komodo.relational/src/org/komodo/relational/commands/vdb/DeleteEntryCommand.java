/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.vdb;

import static org.komodo.relational.commands.vdb.VdbCommandMessages.General.MISSING_ENTRY_NAME;
import org.komodo.relational.vdb.Vdb;
import org.komodo.shell.api.WorkspaceStatus;

/**
 * A shell command to delete an entry from a VDB.
 */
public final class DeleteEntryCommand extends VdbShellCommand {

    static final String NAME = "delete-entry"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public DeleteEntryCommand( final WorkspaceStatus status ) {
        super( NAME, true, status );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#doExecute()
     */
    @Override
    protected boolean doExecute() throws Exception {
        final String entryName = requiredArgument( 0, getMessage(MISSING_ENTRY_NAME) );

        final Vdb vdb = getVdb();
        vdb.removeEntry( getTransaction(), entryName );

        return true;
    }

}
