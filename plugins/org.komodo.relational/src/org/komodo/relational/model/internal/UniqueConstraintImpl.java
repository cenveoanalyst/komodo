/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.model.internal;

import org.komodo.relational.RelationalProperties;
import org.komodo.relational.internal.AdapterFactory;
import org.komodo.relational.internal.RelationalModelFactory;
import org.komodo.relational.internal.TypeResolver;
import org.komodo.relational.model.Table;
import org.komodo.relational.model.UniqueConstraint;
import org.komodo.repository.ObjectImpl;
import org.komodo.spi.KException;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.spi.repository.KomodoType;
import org.komodo.spi.repository.Repository;
import org.komodo.spi.repository.Repository.UnitOfWork;
import org.modeshape.sequencer.ddl.dialect.teiid.TeiidDdlLexicon.Constraint;

/**
 * An implementation of a relational model unique constraint.
 */
public final class UniqueConstraintImpl extends TableConstraintImpl implements UniqueConstraint {

    /**
     * The resolver of a {@link UniqueConstraint}.
     */
    public static final TypeResolver RESOLVER = new TypeResolver() {

        @Override
        public KomodoType identifier() {
            return IDENTIFIER;
        }

        @Override
        public Class<? extends KomodoObject> owningClass() {
            return UniqueConstraintImpl.class;
        }

        /**
         * {@inheritDoc}
         *
         * @see org.komodo.relational.internal.TypeResolver#resolvable(org.komodo.spi.repository.Repository.UnitOfWork,
         *      org.komodo.spi.repository.Repository, org.komodo.spi.repository.KomodoObject)
         */
        @Override
        public boolean resolvable( final UnitOfWork transaction,
                                   final Repository repository,
                                   final KomodoObject kobject ) {
            try {
                ObjectImpl.validateType(transaction, repository, kobject, Constraint.TABLE_ELEMENT);
                ObjectImpl.validatePropertyValue(transaction,
                                                 repository,
                                                 kobject,
                                                 Constraint.TYPE,
                                                 UniqueConstraint.CONSTRAINT_TYPE.toString());
                return true;
            } catch (final Exception e) {
                // not resolvable
            }

            return false;
        }

        /**
         * {@inheritDoc}
         *
         * @see org.komodo.relational.internal.TypeResolver#resolve(org.komodo.spi.repository.Repository.UnitOfWork,
         *      org.komodo.spi.repository.Repository, org.komodo.spi.repository.KomodoObject)
         */
        @Override
        public UniqueConstraint resolve( final UnitOfWork transaction,
                                         final Repository repository,
                                         final KomodoObject kobject ) throws KException {
            return new UniqueConstraintImpl(transaction, repository, kobject.getAbsolutePath());
        }

        @Override
        public UniqueConstraint create(UnitOfWork transaction,
                                                      KomodoObject parent,
                                                      String id,
                                                      RelationalProperties properties) throws KException {
            AdapterFactory adapter = new AdapterFactory(parent.getRepository());
            Table parentTable = adapter.adapt(transaction, parent, Table.class);
            return RelationalModelFactory.createUniqueConstraint(transaction, parent.getRepository(), parentTable, id);
        }

    };

    /**
     * @param uow
     *        the transaction (can be <code>null</code> if update should be automatically committed)
     * @param repository
     *        the repository where the relational object exists (cannot be <code>null</code>)
     * @param workspacePath
     *        the workspace relative path (cannot be empty)
     * @throws KException
     *         if an error occurs or if node at specified path is not a unique constraint
     */
    public UniqueConstraintImpl( final UnitOfWork uow,
                                 final Repository repository,
                                 final String workspacePath ) throws KException {
        super(uow, repository, workspacePath);
    }

    @Override
    public KomodoType getTypeIdentifier(UnitOfWork uow) {
        return RESOLVER.identifier();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.model.TableConstraint#getConstraintType()
     */
    @Override
    public ConstraintType getConstraintType() {
        return CONSTRAINT_TYPE;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.spi.repository.KomodoObject#getTypeId()
     */
    @Override
    public int getTypeId() {
        return TYPE_ID;
    }

}
