
package arina.maplab.definitions.mapforce.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="schema" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="inputinstance">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="outputinstance">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="instanceroot">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="userdefinedschemalocation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "document")
public class Document {

    @XmlAttribute(name = "schema", required = true)
    protected String schema;
    @XmlAttribute(name = "inputinstance")
    protected String inputinstance;
    @XmlAttribute(name = "outputinstance")
    protected String outputinstance;
    @XmlAttribute(name = "instanceroot")
    protected String instanceroot;
    @XmlAttribute(name = "userdefinedschemalocation")
    protected String userdefinedschemalocation;

    /**
     * Gets the value of the schema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the value of the schema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchema(String value) {
        this.schema = value;
    }

    /**
     * Gets the value of the inputinstance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputinstance() {
        return inputinstance;
    }

    /**
     * Sets the value of the inputinstance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputinstance(String value) {
        this.inputinstance = value;
    }

    /**
     * Gets the value of the outputinstance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputinstance() {
        return outputinstance;
    }

    /**
     * Sets the value of the outputinstance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputinstance(String value) {
        this.outputinstance = value;
    }

    /**
     * Gets the value of the instanceroot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstanceroot() {
        return instanceroot;
    }

    /**
     * Sets the value of the instanceroot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstanceroot(String value) {
        this.instanceroot = value;
    }

    /**
     * Gets the value of the userdefinedschemalocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserdefinedschemalocation() {
        return userdefinedschemalocation;
    }

    /**
     * Sets the value of the userdefinedschemalocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserdefinedschemalocation(String value) {
        this.userdefinedschemalocation = value;
    }

}
