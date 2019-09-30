
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
 *       &lt;sequence>
 *         &lt;element ref="{}edges" minOccurs="0"/>
 *         &lt;element ref="{}vertices" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="directed" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "edges",
    "vertices"
})
@XmlRootElement(name = "graph")
public class Graph {

    protected Edges edges;
    protected Vertices vertices;
    @XmlAttribute(name = "directed", required = true)
    protected int directed;

    /**
     * Gets the value of the edges property.
     * 
     * @return
     *     possible object is
     *     {@link Edges }
     *     
     */
    public Edges getEdges() {
        return edges;
    }

    /**
     * Sets the value of the edges property.
     * 
     * @param value
     *     allowed object is
     *     {@link Edges }
     *     
     */
    public void setEdges(Edges value) {
        this.edges = value;
    }

    /**
     * Gets the value of the vertices property.
     * 
     * @return
     *     possible object is
     *     {@link Vertices }
     *     
     */
    public Vertices getVertices() {
        return vertices;
    }

    /**
     * Sets the value of the vertices property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vertices }
     *     
     */
    public void setVertices(Vertices value) {
        this.vertices = value;
    }

    /**
     * Gets the value of the directed property.
     * 
     */
    public int getDirected() {
        return directed;
    }

    /**
     * Sets the value of the directed property.
     * 
     */
    public void setDirected(int value) {
        this.directed = value;
    }

}
