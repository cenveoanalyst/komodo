//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.01 at 10:48:40 AM BST 
//


package org.komodo.teiid.client.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for translatorPropertyEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="translatorPropertyEnumType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="rest"/&gt;
 *     &lt;enumeration value="soap"/&gt;
 *     &lt;enumeration value="wsdl"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "translatorPropertyEnumType")
@XmlEnum
public enum TranslatorPropertyEnumType {

    @XmlEnumValue("rest")
    REST("rest"),
    @XmlEnumValue("soap")
    SOAP("soap"),
    @XmlEnumValue("wsdl")
    WSDL("wsdl");
    private final String value;

    TranslatorPropertyEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TranslatorPropertyEnumType fromValue(String v) {
        for (TranslatorPropertyEnumType c: TranslatorPropertyEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
