
package arina.maplab.definitions.mapforce.model;

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
 *         &lt;element ref="{}edges"/>
 *       &lt;/sequence>
 *       &lt;attribute name="vertexkey" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "edges"
})
@XmlRootElement(name = "vertex")
public class Vertex {

    @XmlElement(required = true)
    protected Edges edges;
    @XmlAttribute(name = "vertexkey", required = true)
    protected int vertexkey;

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
     * Gets the value of the vertexkey property.
     * 
     */
    public int getVertexkey() {
        return vertexkey;
    }

    /**
     * Sets the value of the vertexkey property.
     * 
     */
    public void setVertexkey(int value) {
        this.vertexkey = value;
    }

}
