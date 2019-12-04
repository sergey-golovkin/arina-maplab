
package arina.maplab.definitions.mapforce.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="resources" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="component" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="properties">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="SelectedLanguage">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="builtin"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="MakePathsAbsoluteOnGeneration" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="XSLTTargetEncoding" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ShowSchemaTypes" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="ShowAnnotations" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="ShowLibraryNameInHeader" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element ref="{}structure"/>
 *                   &lt;element ref="{}connections" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="blackbox" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="uid" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="editable" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="library" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="inline" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="library" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "resources",
    "component"
})
@XmlRootElement(name = "mapping")
public class Mapping {

    protected Object resources;
    protected List<Mapping.Component> component;
    @XmlAttribute(name = "version", required = true)
    protected int version;
    @XmlAttribute(name = "library")
    protected String library;

    /**
     * Gets the value of the resources property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setResources(Object value) {
        this.resources = value;
    }

    /**
     * Gets the value of the component property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the component property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Mapping.Component }
     * 
     * 
     */
    public List<Mapping.Component> getComponent() {
        if (component == null) {
            component = new ArrayList<Mapping.Component>();
        }
        return this.component;
    }

    /**
     * Gets the value of the version property.
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     * Gets the value of the library property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibrary() {
        return library;
    }

    /**
     * Sets the value of the library property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibrary(String value) {
        this.library = value;
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
     *         &lt;element name="properties">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="SelectedLanguage">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="builtin"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="MakePathsAbsoluteOnGeneration" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="XSLTTargetEncoding" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="ShowSchemaTypes" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="ShowAnnotations" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="ShowLibraryNameInHeader" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element ref="{}structure"/>
     *         &lt;element ref="{}connections" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="blackbox" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="uid" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="editable" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="library" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="inline" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "properties",
        "description",
        "structure",
        "connections"
    })
    public static class Component {

        @XmlElement(required = true)
        protected Mapping.Component.Properties properties;
        protected String description;
        @XmlElement(required = true)
        protected Structure structure;
        protected Connections connections;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "blackbox")
        protected Integer blackbox;
        @XmlAttribute(name = "uid")
        protected Integer uid;
        @XmlAttribute(name = "editable", required = true)
        protected int editable;
        @XmlAttribute(name = "library")
        protected String library;
        @XmlAttribute(name = "inline")
        protected Integer inline;

        /**
         * Gets the value of the properties property.
         * 
         * @return
         *     possible object is
         *     {@link Mapping.Component.Properties }
         *     
         */
        public Mapping.Component.Properties getProperties() {
            return properties;
        }

        /**
         * Sets the value of the properties property.
         * 
         * @param value
         *     allowed object is
         *     {@link Mapping.Component.Properties }
         *     
         */
        public void setProperties(Mapping.Component.Properties value) {
            this.properties = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the structure property.
         * 
         * @return
         *     possible object is
         *     {@link Structure }
         *     
         */
        public Structure getStructure() {
            return structure;
        }

        /**
         * Sets the value of the structure property.
         * 
         * @param value
         *     allowed object is
         *     {@link Structure }
         *     
         */
        public void setStructure(Structure value) {
            this.structure = value;
        }

        /**
         * Gets the value of the connections property.
         * 
         * @return
         *     possible object is
         *     {@link Connections }
         *     
         */
        public Connections getConnections() {
            return connections;
        }

        /**
         * Sets the value of the connections property.
         * 
         * @param value
         *     allowed object is
         *     {@link Connections }
         *     
         */
        public void setConnections(Connections value) {
            this.connections = value;
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
         * Gets the value of the blackbox property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getBlackbox() {
            return blackbox;
        }

        /**
         * Sets the value of the blackbox property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setBlackbox(Integer value) {
            this.blackbox = value;
        }

        /**
         * Gets the value of the uid property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getUid() {
            return uid;
        }

        /**
         * Sets the value of the uid property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setUid(Integer value) {
            this.uid = value;
        }

        /**
         * Gets the value of the editable property.
         * 
         */
        public int getEditable() {
            return editable;
        }

        /**
         * Sets the value of the editable property.
         * 
         */
        public void setEditable(int value) {
            this.editable = value;
        }

        /**
         * Gets the value of the library property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLibrary() {
            return library;
        }

        /**
         * Sets the value of the library property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLibrary(String value) {
            this.library = value;
        }

        /**
         * Gets the value of the inline property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getInline() {
            return inline;
        }

        /**
         * Sets the value of the inline property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setInline(Integer value) {
            this.inline = value;
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
         *       &lt;attribute name="SelectedLanguage">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="builtin"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="MakePathsAbsoluteOnGeneration" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="XSLTTargetEncoding" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="ShowSchemaTypes" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="ShowAnnotations" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="ShowLibraryNameInHeader" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Properties {

            @XmlAttribute(name = "SelectedLanguage")
            protected String selectedLanguage;
            @XmlAttribute(name = "MakePathsAbsoluteOnGeneration")
            protected Integer makePathsAbsoluteOnGeneration;
            @XmlAttribute(name = "XSLTTargetEncoding")
            protected String xsltTargetEncoding;
            @XmlAttribute(name = "ShowSchemaTypes")
            protected Integer showSchemaTypes;
            @XmlAttribute(name = "ShowAnnotations")
            protected Integer showAnnotations;
            @XmlAttribute(name = "ShowLibraryNameInHeader")
            protected Integer showLibraryNameInHeader;

            /**
             * Gets the value of the selectedLanguage property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSelectedLanguage() {
                return selectedLanguage;
            }

            /**
             * Sets the value of the selectedLanguage property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSelectedLanguage(String value) {
                this.selectedLanguage = value;
            }

            /**
             * Gets the value of the makePathsAbsoluteOnGeneration property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getMakePathsAbsoluteOnGeneration() {
                return makePathsAbsoluteOnGeneration;
            }

            /**
             * Sets the value of the makePathsAbsoluteOnGeneration property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setMakePathsAbsoluteOnGeneration(Integer value) {
                this.makePathsAbsoluteOnGeneration = value;
            }

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
             * Gets the value of the showSchemaTypes property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getShowSchemaTypes() {
                return showSchemaTypes;
            }

            /**
             * Sets the value of the showSchemaTypes property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setShowSchemaTypes(Integer value) {
                this.showSchemaTypes = value;
            }

            /**
             * Gets the value of the showAnnotations property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getShowAnnotations() {
                return showAnnotations;
            }

            /**
             * Sets the value of the showAnnotations property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setShowAnnotations(Integer value) {
                this.showAnnotations = value;
            }

            /**
             * Gets the value of the showLibraryNameInHeader property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getShowLibraryNameInHeader() {
                return showLibraryNameInHeader;
            }

            /**
             * Sets the value of the showLibraryNameInHeader property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setShowLibraryNameInHeader(Integer value) {
                this.showLibraryNameInHeader = value;
            }

        }

    }

}
