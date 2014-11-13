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

package org.teiid.query.sql.v8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.komodo.spi.runtime.version.TeiidVersion;
import org.komodo.spi.runtime.version.DefaultTeiidVersion.Version;
import org.teiid.query.sql.AbstractTestFactory;
import org.teiid.query.sql.AbstractTestQueryParser;
import org.teiid.query.sql.lang.CreateImpl;

@SuppressWarnings( {"javadoc"} )
public class Test8Create extends AbstractTestQueryParser {

	// ################################## FRAMEWORK ################################
	
    private Test8Factory factory;

    protected Test8Create(TeiidVersion teiidVersion) {
        super(teiidVersion);
    }

    /**
     *
     */
    public Test8Create() {
        this(Version.TEIID_8_0.get());
    }

    @Override
    protected AbstractTestFactory getFactory() {
        if (factory == null)
            factory = new Test8Factory(parser);

        return factory;
    }
	
	// ################################## TEST HELPERS ################################	

    public CreateImpl sample1() {
        CreateImpl create = getFactory().newCreate();
        create.setTable(getFactory().newGroupSymbol("temp_table"));//$NON-NLS-1$

        List elements = new ArrayList();
        elements.add(getFactory().newElementSymbol("a")); //$NON-NLS-1$
        elements.add(getFactory().newElementSymbol("b")); //$NON-NLS-1$

        create.setElementSymbolsAsColumns(elements);
        return create;
    }

    public CreateImpl sample2() {
        CreateImpl create = getFactory().newCreate();
        create.setTable(getFactory().newGroupSymbol("temp_table2"));//$NON-NLS-1$

        List elements = new ArrayList();
        elements.add(getFactory().newElementSymbol("a")); //$NON-NLS-1$
        elements.add(getFactory().newElementSymbol("b")); //$NON-NLS-1$

        create.setElementSymbolsAsColumns(elements);
        return create;
    }
			
	// ################################## ACTUAL TESTS ################################


    @Test
    public void testSelfEquivalence() {
        CreateImpl c1 = sample1();
        assertEquals(c1, c1);
    }

    @Test
    public void testEquivalence() {
        CreateImpl c1 = sample1();
        CreateImpl c2 = sample1();
        assertEquals(c1, c2);
    }

    @Test
    public void testNonEquivalence() {
        CreateImpl c1 = sample1();
        CreateImpl c2 = sample2();
        assertNotEquals(c1, c2);
    }
}
