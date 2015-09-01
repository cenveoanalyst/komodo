//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.01 at 10:48:40 AM BST 
//


package org.komodo.teiid.client.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import com.fasterxml.jackson.annotation.*;
import javax.xml.bind.annotation.XmlType;


/**
 * Model Property
 * 
 * <p>Java class for modelProperty complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modelProperty"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;&gt;property"&gt;
 *       &lt;attribute name="suggestedValue" type="{}modelPropertyEnumType" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modelProperty")
public class ModelProperty
    extends Property
{

    @XmlAttribute(name = "suggestedValue")
    protected ModelPropertyEnumType suggestedValue;

    /**
     * Gets the value of the suggestedValue property.
     * 
     * @return
     *     possible object is
     *     {@link ModelPropertyEnumType }
     *     
     */
    public ModelPropertyEnumType getSuggestedValue() {
        return suggestedValue;
    }

    /**
     * Sets the value of the suggestedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelPropertyEnumType }
     *     
     */
    public void setSuggestedValue(ModelPropertyEnumType value) {
        this.suggestedValue = value;
    }

}
