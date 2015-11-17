/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.komodo.relational.commands.schema;

import org.junit.Test;

/**
 * Test class for {@link SchemaCommandsI18n}.
 */
@SuppressWarnings("javadoc")
public final class SchemaCommandsI18nTest {

    @Test
    public void shouldNotHaveErrors() throws Exception {
        SchemaCommandsI18n.ddlExported.length();
    }

}
