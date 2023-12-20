
package org.geojsf.model.xml.geoserver;

import java.io.Serializable;
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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.geojsf.org/geoserver}workspace"/&gt;
 *         &lt;element ref="{http://www.geojsf.org/geoserver}postgis"/&gt;
 *         &lt;element ref="{http://www.geojsf.org/geoserver}shapeDir"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "workspace",
    "postgis",
    "shapeDir"
})
@XmlRootElement(name = "dataStore")
public class DataStore
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Workspace workspace;
    @XmlElement(required = true)
    protected Postgis postgis;
    @XmlElement(required = true)
    protected ShapeDir shapeDir;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;

    /**
     * Gets the value of the workspace property.
     * 
     * @return
     *     possible object is
     *     {@link Workspace }
     *     
     */
    public Workspace getWorkspace() {
        return workspace;
    }

    /**
     * Sets the value of the workspace property.
     * 
     * @param value
     *     allowed object is
     *     {@link Workspace }
     *     
     */
    public void setWorkspace(Workspace value) {
        this.workspace = value;
    }

    /**
     * Gets the value of the postgis property.
     * 
     * @return
     *     possible object is
     *     {@link Postgis }
     *     
     */
    public Postgis getPostgis() {
        return postgis;
    }

    /**
     * Sets the value of the postgis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Postgis }
     *     
     */
    public void setPostgis(Postgis value) {
        this.postgis = value;
    }

    /**
     * Gets the value of the shapeDir property.
     * 
     * @return
     *     possible object is
     *     {@link ShapeDir }
     *     
     */
    public ShapeDir getShapeDir() {
        return shapeDir;
    }

    /**
     * Sets the value of the shapeDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShapeDir }
     *     
     */
    public void setShapeDir(ShapeDir value) {
        this.shapeDir = value;
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
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

}
