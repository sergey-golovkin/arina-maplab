
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
 *       &lt;attribute name="datatype" type="{}datatype" />
 *       &lt;attribute name="previewvalue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="usepreviewvalue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "input")
public class Input {

    @XmlAttribute(name = "datatype")
    protected Datatype datatype;
    @XmlAttribute(name = "previewvalue")
    protected String previewvalue;
    @XmlAttribute(name = "usepreviewvalue")
    protected String usepreviewvalue;

    /**
     * Gets the value of the datatype property.
     * 
     * @return
     *     possible object is
     *     {@link Datatype }
     *     
     */
    public Datatype getDatatype() {
        return datatype;
    }

    /**
     * Sets the value of the datatype property.
     * 
     * @param value
     *     allowed object is
     *     {@link Datatype }
     *     
     */
    public void setDatatype(Datatype value) {
        this.datatype = value;
    }

    /**
     * Gets the value of the previewvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreviewvalue() {
        return previewvalue;
    }

    /**
     * Sets the value of the previewvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreviewvalue(String value) {
        this.previewvalue = value;
    }

    /**
     * Gets the value of the usepreviewvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsepreviewvalue() {
        return usepreviewvalue;
    }

    /**
     * Sets the value of the usepreviewvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsepreviewvalue(String value) {
        this.usepreviewvalue = value;
    }

}
