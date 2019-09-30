
package arina.maplab.definitions.mapforce.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="ranges" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="range" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="typeselections" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="type">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{}entry"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}inputnodefunctions" minOccurs="0"/>
 *         &lt;element ref="{}outputnodefunctions" minOccurs="0"/>
 *         &lt;element ref="{}entry" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ns" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="expanded" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="casttotargettypemode">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="cast-in-subtree"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="inpkey" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="outkey" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="type">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="attribute"/>
 *             &lt;enumeration value="json-property"/>
 *             &lt;enumeration value="json-item"/>
 *             &lt;enumeration value="table"/>
 *             &lt;enumeration value="xml-type"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="componentid" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="clone" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="displayselectionmode">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="selection"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="datatype" type="{}datatype" />
 *       &lt;attribute name="annotation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="show-schema-children" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="use-generic-elements" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ranges",
    "typeselections",
    "inputnodefunctions",
    "outputnodefunctions",
    "entry"
})
@XmlRootElement(name = "entry")
public class Entry {

    protected Entry.Ranges ranges;
    protected Entry.Typeselections typeselections;
    protected Nodefunctions inputnodefunctions;
    protected Nodefunctions outputnodefunctions;
    protected List<Entry> entry;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "ns")
    @XmlSchemaType(name = "anyURI")
    protected String ns;
    @XmlAttribute(name = "expanded")
    protected Integer expanded;
    @XmlAttribute(name = "casttotargettypemode")
    protected String casttotargettypemode;
    @XmlAttribute(name = "inpkey")
    protected Integer inpkey;
    @XmlAttribute(name = "outkey")
    protected Integer outkey;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "componentid")
    protected Integer componentid;
    @XmlAttribute(name = "clone")
    protected Integer clone;
    @XmlAttribute(name = "displayselectionmode")
    protected String displayselectionmode;
    @XmlAttribute(name = "datatype")
    protected Datatype datatype;
    @XmlAttribute(name = "annotation")
    protected String annotation;
    @XmlAttribute(name = "show-schema-children")
    protected Integer showSchemaChildren;
    @XmlAttribute(name = "use-generic-elements")
    protected Integer useGenericElements;

    /**
     * Gets the value of the ranges property.
     * 
     * @return
     *     possible object is
     *     {@link Entry.Ranges }
     *     
     */
    public Entry.Ranges getRanges() {
        return ranges;
    }

    /**
     * Sets the value of the ranges property.
     * 
     * @param value
     *     allowed object is
     *     {@link Entry.Ranges }
     *     
     */
    public void setRanges(Entry.Ranges value) {
        this.ranges = value;
    }

    /**
     * Gets the value of the typeselections property.
     * 
     * @return
     *     possible object is
     *     {@link Entry.Typeselections }
     *     
     */
    public Entry.Typeselections getTypeselections() {
        return typeselections;
    }

    /**
     * Sets the value of the typeselections property.
     * 
     * @param value
     *     allowed object is
     *     {@link Entry.Typeselections }
     *     
     */
    public void setTypeselections(Entry.Typeselections value) {
        this.typeselections = value;
    }

    /**
     * Gets the value of the inputnodefunctions property.
     * 
     * @return
     *     possible object is
     *     {@link Nodefunctions }
     *     
     */
    public Nodefunctions getInputnodefunctions() {
        return inputnodefunctions;
    }

    /**
     * Sets the value of the inputnodefunctions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Nodefunctions }
     *     
     */
    public void setInputnodefunctions(Nodefunctions value) {
        this.inputnodefunctions = value;
    }

    /**
     * Gets the value of the outputnodefunctions property.
     * 
     * @return
     *     possible object is
     *     {@link Nodefunctions }
     *     
     */
    public Nodefunctions getOutputnodefunctions() {
        return outputnodefunctions;
    }

    /**
     * Sets the value of the outputnodefunctions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Nodefunctions }
     *     
     */
    public void setOutputnodefunctions(Nodefunctions value) {
        this.outputnodefunctions = value;
    }

    /**
     * Gets the value of the entry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Entry }
     * 
     * 
     */
    public List<Entry> getEntry() {
        if (entry == null) {
            entry = new ArrayList<Entry>();
        }
        return this.entry;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the ns property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNs() {
        return ns;
    }

    /**
     * Sets the value of the ns property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNs(String value) {
        this.ns = value;
    }

    /**
     * Gets the value of the expanded property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getExpanded() {
        return expanded;
    }

    /**
     * Sets the value of the expanded property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setExpanded(Integer value) {
        this.expanded = value;
    }

    /**
     * Gets the value of the casttotargettypemode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCasttotargettypemode() {
        return casttotargettypemode;
    }

    /**
     * Sets the value of the casttotargettypemode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCasttotargettypemode(String value) {
        this.casttotargettypemode = value;
    }

    /**
     * Gets the value of the inpkey property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInpkey() {
        return inpkey;
    }

    /**
     * Sets the value of the inpkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInpkey(Integer value) {
        this.inpkey = value;
    }

    /**
     * Gets the value of the outkey property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOutkey() {
        return outkey;
    }

    /**
     * Sets the value of the outkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOutkey(Integer value) {
        this.outkey = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the componentid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getComponentid() {
        return componentid;
    }

    /**
     * Sets the value of the componentid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setComponentid(Integer value) {
        this.componentid = value;
    }

    /**
     * Gets the value of the clone property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClone() {
        return clone;
    }

    /**
     * Sets the value of the clone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClone(Integer value) {
        this.clone = value;
    }

    /**
     * Gets the value of the displayselectionmode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayselectionmode() {
        return displayselectionmode;
    }

    /**
     * Sets the value of the displayselectionmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayselectionmode(String value) {
        this.displayselectionmode = value;
    }

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
     * Gets the value of the annotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the value of the annotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

    /**
     * Gets the value of the showSchemaChildren property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getShowSchemaChildren() {
        return showSchemaChildren;
    }

    /**
     * Sets the value of the showSchemaChildren property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setShowSchemaChildren(Integer value) {
        this.showSchemaChildren = value;
    }

    /**
     * Gets the value of the useGenericElements property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUseGenericElements() {
        return useGenericElements;
    }

    /**
     * Sets the value of the useGenericElements property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUseGenericElements(Integer value) {
        this.useGenericElements = value;
    }


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
     *         &lt;element name="range" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "range"
    })
    public static class Ranges {

        protected List<Entry.Ranges.Range> range;

        /**
         * Gets the value of the range property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the range property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRange().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Entry.Ranges.Range }
         * 
         * 
         */
        public List<Entry.Ranges.Range> getRange() {
            if (range == null) {
                range = new ArrayList<Entry.Ranges.Range>();
            }
            return this.range;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Range {

            @XmlAttribute(name = "id")
            protected Integer id;
            @XmlAttribute(name = "start")
            protected Integer start;

            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setId(Integer value) {
                this.id = value;
            }

            /**
             * Gets the value of the start property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getStart() {
                return start;
            }

            /**
             * Sets the value of the start property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setStart(Integer value) {
                this.start = value;
            }

        }

    }


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
     *         &lt;element name="type">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{}entry"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "type"
    })
    public static class Typeselections {

        @XmlElement(required = true)
        protected Entry.Typeselections.Type type;

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link Entry.Typeselections.Type }
         *     
         */
        public Entry.Typeselections.Type getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link Entry.Typeselections.Type }
         *     
         */
        public void setType(Entry.Typeselections.Type value) {
            this.type = value;
        }


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
         *         &lt;element ref="{}entry"/>
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
            "entry"
        })
        public static class Type {

            @XmlElement(required = true)
            protected Entry entry;

            /**
             * Gets the value of the entry property.
             * 
             * @return
             *     possible object is
             *     {@link Entry }
             *     
             */
            public Entry getEntry() {
                return entry;
            }

            /**
             * Sets the value of the entry property.
             * 
             * @param value
             *     allowed object is
             *     {@link Entry }
             *     
             */
            public void setEntry(Entry value) {
                this.entry = value;
            }

        }

    }

}
