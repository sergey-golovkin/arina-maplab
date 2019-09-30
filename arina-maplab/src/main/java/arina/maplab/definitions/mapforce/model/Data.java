
package arina.maplab.definitions.mapforce.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *       &lt;sequence>
 *         &lt;element ref="{}root" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}document" minOccurs="0"/>
 *         &lt;element ref="{}constant" minOccurs="0"/>
 *         &lt;element ref="{}input" minOccurs="0"/>
 *         &lt;element ref="{}output" minOccurs="0"/>
 *         &lt;element ref="{}wsdl" minOccurs="0"/>
 *         &lt;element ref="{}parameter" minOccurs="0"/>
 *         &lt;element ref="{}exception" minOccurs="0"/>
 *         &lt;element ref="{}valuemap" minOccurs="0"/>
 *         &lt;element ref="{}json" minOccurs="0"/>
 *         &lt;element ref="{}sort" minOccurs="0"/>
 *         &lt;element ref="{}text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "root",
    "document",
    "constant",
    "input",
    "output",
    "wsdl",
    "parameter",
    "exception",
    "valuemap",
    "json",
    "sort",
    "text"
})
@XmlRootElement(name = "data")
public class Data {

    protected List<Root> root;
    protected Document document;
    protected Constant constant;
    protected Input input;
    protected Output output;
    protected Wsdl wsdl;
    protected Parameter parameter;
    protected Object exception;
    protected Valuemap valuemap;
    protected Json json;
    protected Sort sort;
    protected Text text;

    /**
     * Gets the value of the root property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the root property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoot().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Root }
     * 
     * 
     */
    public List<Root> getRoot() {
        if (root == null) {
            root = new ArrayList<Root>();
        }
        return this.root;
    }

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     {@link Document }
     *     
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     {@link Document }
     *     
     */
    public void setDocument(Document value) {
        this.document = value;
    }

    /**
     * Gets the value of the constant property.
     * 
     * @return
     *     possible object is
     *     {@link Constant }
     *     
     */
    public Constant getConstant() {
        return constant;
    }

    /**
     * Sets the value of the constant property.
     * 
     * @param value
     *     allowed object is
     *     {@link Constant }
     *     
     */
    public void setConstant(Constant value) {
        this.constant = value;
    }

    /**
     * Gets the value of the input property.
     * 
     * @return
     *     possible object is
     *     {@link Input }
     *     
     */
    public Input getInput() {
        return input;
    }

    /**
     * Sets the value of the input property.
     * 
     * @param value
     *     allowed object is
     *     {@link Input }
     *     
     */
    public void setInput(Input value) {
        this.input = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * @return
     *     possible object is
     *     {@link Output }
     *     
     */
    public Output getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     * 
     * @param value
     *     allowed object is
     *     {@link Output }
     *     
     */
    public void setOutput(Output value) {
        this.output = value;
    }

    /**
     * Gets the value of the wsdl property.
     * 
     * @return
     *     possible object is
     *     {@link Wsdl }
     *     
     */
    public Wsdl getWsdl() {
        return wsdl;
    }

    /**
     * Sets the value of the wsdl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Wsdl }
     *     
     */
    public void setWsdl(Wsdl value) {
        this.wsdl = value;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link Parameter }
     *     
     */
    public Parameter getParameter() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Parameter }
     *     
     */
    public void setParameter(Parameter value) {
        this.parameter = value;
    }

    /**
     * Gets the value of the exception property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getException() {
        return exception;
    }

    /**
     * Sets the value of the exception property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setException(Object value) {
        this.exception = value;
    }

    /**
     * Gets the value of the valuemap property.
     * 
     * @return
     *     possible object is
     *     {@link Valuemap }
     *     
     */
    public Valuemap getValuemap() {
        return valuemap;
    }

    /**
     * Sets the value of the valuemap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Valuemap }
     *     
     */
    public void setValuemap(Valuemap value) {
        this.valuemap = value;
    }

    /**
     * Gets the value of the json property.
     * 
     * @return
     *     possible object is
     *     {@link Json }
     *     
     */
    public Json getJson() {
        return json;
    }

    /**
     * Sets the value of the json property.
     * 
     * @param value
     *     allowed object is
     *     {@link Json }
     *     
     */
    public void setJson(Json value) {
        this.json = value;
    }

    /**
     * Gets the value of the sort property.
     * 
     * @return
     *     possible object is
     *     {@link Sort }
     *     
     */
    public Sort getSort() {
        return sort;
    }

    /**
     * Sets the value of the sort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sort }
     *     
     */
    public void setSort(Sort value) {
        this.sort = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link Text }
     *     
     */
    public Text getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link Text }
     *     
     */
    public void setText(Text value) {
        this.text = value;
    }

}
