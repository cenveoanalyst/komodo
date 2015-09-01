//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.01 at 10:48:40 AM BST 
//


package org.komodo.teiid.client.schema;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.komodo.teiid.client.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.komodo.teiid.client.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Vdb }
     * 
     */
    public Vdb createVdb() {
        return new Vdb();
    }

    /**
     * Create an instance of {@link VdbProperty }
     * 
     */
    public VdbProperty createVdbProperty() {
        return new VdbProperty();
    }

    /**
     * Create an instance of {@link ImportVdb }
     * 
     */
    public ImportVdb createImportVdb() {
        return new ImportVdb();
    }

    /**
     * Create an instance of {@link Model }
     * 
     */
    public Model createModel() {
        return new Model();
    }

    /**
     * Create an instance of {@link Translator }
     * 
     */
    public Translator createTranslator() {
        return new Translator();
    }

    /**
     * Create an instance of {@link DataRole }
     * 
     */
    public DataRole createDataRole() {
        return new DataRole();
    }

    /**
     * Create an instance of {@link Entry }
     * 
     */
    public Entry createEntry() {
        return new Entry();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link ModelProperty }
     * 
     */
    public ModelProperty createModelProperty() {
        return new ModelProperty();
    }

    /**
     * Create an instance of {@link TranslatorProperty }
     * 
     */
    public TranslatorProperty createTranslatorProperty() {
        return new TranslatorProperty();
    }

    /**
     * Create an instance of {@link EntryProperty }
     * 
     */
    public EntryProperty createEntryProperty() {
        return new EntryProperty();
    }

    /**
     * Create an instance of {@link Permission }
     * 
     */
    public Permission createPermission() {
        return new Permission();
    }

    /**
     * Create an instance of {@link Condition }
     * 
     */
    public Condition createCondition() {
        return new Condition();
    }

    /**
     * Create an instance of {@link Mask }
     * 
     */
    public Mask createMask() {
        return new Mask();
    }

    /**
     * Create an instance of {@link Source }
     * 
     */
    public Source createSource() {
        return new Source();
    }

    /**
     * Create an instance of {@link Metadata }
     * 
     */
    public Metadata createMetadata() {
        return new Metadata();
    }

    /**
     * Create an instance of {@link ValidationError }
     * 
     */
    public ValidationError createValidationError() {
        return new ValidationError();
    }

}
