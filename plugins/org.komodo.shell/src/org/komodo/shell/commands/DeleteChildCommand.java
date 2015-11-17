/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.shell.commands;

import org.komodo.shell.BuiltInShellCommand;
import org.komodo.shell.CommandResultImpl;
import org.komodo.shell.ShellI18n;
import org.komodo.shell.api.CommandResult;
import org.komodo.shell.api.ShellCommand;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.utils.StringUtils;
import org.komodo.utils.i18n.I18n;

/**
 * A {@link ShellCommand command} that deletes a child from a {@link KomodoObject}.
 * <p>
 * Usage:
 * <p>
 * <code>&nbsp;&nbsp;
 * delete-child &lt;child-name&gt; [child-type]
 * </code>
 */
public class DeleteChildCommand extends BuiltInShellCommand {

    /**
     * The command name.
     */
    public static final String NAME = "delete-child"; //$NON-NLS-1$

    /**
     * @param status
     *        the workspace status (cannot be <code>null</code>)
     */
    public DeleteChildCommand( final WorkspaceStatus status ) {
        super( status, NAME );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#doExecute()
     */
    @Override
    protected CommandResult doExecute() {
        try {
            final String childNameArg = requiredArgument( 0, I18n.bind( ShellI18n.missingChildNameForDelete ) );
            final String childTypeArg = optionalArgument( 1 );

            final KomodoObject kobject = getContext();
            KomodoObject childObject = null;
            // Determine if child exists before attempting delete.
            if(!StringUtils.isBlank(childTypeArg)) {
                if(!kobject.hasChild(getTransaction(), childNameArg, childTypeArg)) {
                    return new CommandResultImpl( false,
                                                  I18n.bind( ShellI18n.noChildWithNameAndType, childNameArg, childTypeArg ),
                                                  null );
                }
                childObject = kobject.getChild(getTransaction(), childNameArg, childTypeArg);
            } else {
                if ( !kobject.hasChild( getTransaction(), childNameArg ) ) {
                    return new CommandResultImpl( false, I18n.bind( ShellI18n.noChildWithName, childNameArg ), null );
                }

                childObject = kobject.getChild(getTransaction(), childNameArg);
            }

            if ( childObject == null ) return new CommandResultImpl( false,
                                                                     I18n.bind( ShellI18n.errorGettingChild, childNameArg ),
                                                                     null );

            childObject.remove(getTransaction());
            return new CommandResultImpl( I18n.bind( ShellI18n.childDeleted, childNameArg ) );
        } catch ( final Exception e ) {
            return new CommandResultImpl( e );
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#getMaxArgCount()
     */
    @Override
    protected int getMaxArgCount() {
        return 2;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommand#isValidForCurrentContext()
     */
    @Override
    public boolean isValidForCurrentContext() {
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.commands.datarole.DataRoleShellCommand#printHelpDescription(int)
     */
    @Override
    protected void printHelpDescription( final int indent ) {
        print( indent, I18n.bind( ShellI18n.deleteChildHelp, getName() ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.commands.datarole.DataRoleShellCommand#printHelpExamples(int)
     */
    @Override
    protected void printHelpExamples( final int indent ) {
        print( indent, I18n.bind( ShellI18n.deleteChildExamples ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.commands.datarole.DataRoleShellCommand#printHelpUsage(int)
     */
    @Override
    protected void printHelpUsage( final int indent ) {
        print( indent, I18n.bind( ShellI18n.deleteChildUsage ) );
    }

}
