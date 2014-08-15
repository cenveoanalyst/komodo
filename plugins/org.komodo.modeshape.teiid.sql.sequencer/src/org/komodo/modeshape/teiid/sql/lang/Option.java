/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package org.komodo.modeshape.teiid.sql.lang;

import java.util.Collection;
import org.komodo.modeshape.teiid.parser.LanguageVisitor;
import org.komodo.modeshape.teiid.parser.TeiidParser;
import org.komodo.spi.query.sql.lang.IOption;

public class Option extends ASTNode implements IOption<LanguageVisitor> {

    /**
     * Make Dep token
     */
    public final static String MAKEDEP = "MAKEDEP"; //$NON-NLS-1$

    /**
     * Make Not Dep token
     */
    public final static String MAKENOTDEP = "MAKENOTDEP"; //$NON-NLS-1$

    /**
     * Optional token
     */
    public final static String OPTIONAL = "optional"; //$NON-NLS-1$

    public Option(TeiidParser p, int id) {
        super(p, id);
    }

    @Override
    public Collection<String> getDependentGroups() {
        throw new UnsupportedOperationException();
    }

    /**
     * @param id
     */
    public void addDependentGroup(String id) {
    }

    @Override
    public Collection<String> getNotDependentGroups() {
        throw new UnsupportedOperationException();
    }

    /**
     * @param id
     */
    public void addNotDependentGroup(String id) {
    }

    @Override
    public boolean isNoCache() {
        return false;
    }

    /**
     * @param b
     */
    public void setNoCache(boolean b) {
    }

    @Override
    public Collection<String> getNoCacheGroups() {
        throw new UnsupportedOperationException();
    }

    /**
     * @param id
     */
    public void addNoCacheGroup(String id) {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.getDependentGroups() == null) ? 0 : this.getDependentGroups().hashCode());
        result = prime * result + ((this.getNotDependentGroups() == null) ? 0 : this.getNotDependentGroups().hashCode());
        result = prime * result + (this.isNoCache() ? 1231 : 1237);
        result = prime * result + ((this.getNoCacheGroups() == null) ? 0 : this.getNoCacheGroups().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Option other = (Option)obj;
        if (this.getDependentGroups() == null) {
            if (other.getDependentGroups() != null) return false;
        } else if (!this.getDependentGroups().equals(other.getDependentGroups())) return false;
        if (this.getNotDependentGroups() == null) {
            if (other.getNotDependentGroups() != null) return false;
        } else if (!this.getNotDependentGroups().equals(other.getNotDependentGroups())) return false;
        if (this.isNoCache() != other.isNoCache()) return false;
        if (this.getNoCacheGroups() == null) {
            if (other.getNoCacheGroups() != null) return false;
        } else if (!this.getNoCacheGroups().equals(other.getNoCacheGroups())) return false;
        return true;
    }

    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Option clone() {
        Option clone = new Option(this.getTeiidParser(), this.getId());

        clone.setNoCache(isNoCache());

        if (this.getDependentGroups() != null) {
            for (String group : getDependentGroups()) {
                clone.addDependentGroup(group);
            }
        }

        if (getNotDependentGroups() != null) {
            for (String group : getNotDependentGroups()) {
                clone.addNotDependentGroup(group);
            }
        }

        if (getNoCacheGroups() != null) {
            for (String group : getNoCacheGroups()) {
                clone.addNoCacheGroup(group);
            }
        }

        return clone;
    }

}