
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
 *       &lt;attribute name="XSLTTargetEncoding">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="XSLTDefaultOutput">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="SaveRelativePaths" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="DynamicFilenames" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "properties")
public class Properties {

    @XmlAttribute(name = "XSLTTargetEncoding")
    protected String xsltTargetEncoding;
    @XmlAttribute(name = "XSLTDefaultOutput")
    protected Integer xsltDefaultOutput;
    @XmlAttribute(name = "SaveRelativePaths")
    protected Integer saveRelativePaths;
    @XmlAttribute(name = "DynamicFilenames")
    protected Integer dynamicFilenames;

    /**
     * Gets the value of the xsltTargetEncoding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXSLTTargetEncoding() {
        return xsltTargetEncoding;
    }

    /**
     * Sets the value of the xsltTargetEncoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXSLTTargetEncoding(String value) {
        this.xsltTargetEncoding = value;
    }

    /**
     * Gets the value of the xsltDefaultOutput property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getXSLTDefaultOutput() {
        return xsltDefaultOutput;
    }

    /**
     * Sets the value of the xsltDefaultOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setXSLTDefaultOutput(Integer value) {
        this.xsltDefaultOutput = value;
    }

    /**
     * Gets the value of the saveRelativePaths property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSaveRelativePaths() {
        return saveRelativePaths;
    }

    /**
     * Sets the value of the saveRelativePaths property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSaveRelativePaths(Integer value) {
        this.saveRelativePaths = value;
    }

    /**
     * Gets the value of the dynamicFilenames property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDynamicFilenames() {
        return dynamicFilenames;
    }

    /**
     * Sets the value of the dynamicFilenames property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDynamicFilenames(Integer value) {
        this.dynamicFilenames = value;
    }

}
