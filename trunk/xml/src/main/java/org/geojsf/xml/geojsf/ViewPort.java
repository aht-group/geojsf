
package org.geojsf.xml.geojsf;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.geojsf.org}scale"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="lat" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="lon" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="bottom" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="left" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="right" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="top" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "scale"
})
@XmlRootElement(name = "viewPort")
public class ViewPort
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Scale scale;
    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "lat")
    protected Double lat;
    @XmlAttribute(name = "lon")
    protected Double lon;
    @XmlAttribute(name = "bottom")
    protected Double bottom;
    @XmlAttribute(name = "left")
    protected Double left;
    @XmlAttribute(name = "right")
    protected Double right;
    @XmlAttribute(name = "top")
    protected Double top;

    /**
     * Gets the value of the scale property.
     * 
     * @return
     *     possible object is
     *     {@link Scale }
     *     
     */
    public Scale getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     * 
     * @param value
     *     allowed object is
     *     {@link Scale }
     *     
     */
    public void setScale(Scale value) {
        this.scale = value;
    }

    public boolean isSetScale() {
        return (this.scale!= null);
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(long value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    public void unsetId() {
        this.id = null;
    }

    /**
     * Gets the value of the lat property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets the value of the lat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLat(double value) {
        this.lat = value;
    }

    public boolean isSetLat() {
        return (this.lat!= null);
    }

    public void unsetLat() {
        this.lat = null;
    }

    /**
     * Gets the value of the lon property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getLon() {
        return lon;
    }

    /**
     * Sets the value of the lon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLon(double value) {
        this.lon = value;
    }

    public boolean isSetLon() {
        return (this.lon!= null);
    }

    public void unsetLon() {
        this.lon = null;
    }

    /**
     * Gets the value of the bottom property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getBottom() {
        return bottom;
    }

    /**
     * Sets the value of the bottom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBottom(double value) {
        this.bottom = value;
    }

    public boolean isSetBottom() {
        return (this.bottom!= null);
    }

    public void unsetBottom() {
        this.bottom = null;
    }

    /**
     * Gets the value of the left property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getLeft() {
        return left;
    }

    /**
     * Sets the value of the left property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLeft(double value) {
        this.left = value;
    }

    public boolean isSetLeft() {
        return (this.left!= null);
    }

    public void unsetLeft() {
        this.left = null;
    }

    /**
     * Gets the value of the right property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getRight() {
        return right;
    }

    /**
     * Sets the value of the right property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRight(double value) {
        this.right = value;
    }

    public boolean isSetRight() {
        return (this.right!= null);
    }

    public void unsetRight() {
        this.right = null;
    }

    /**
     * Gets the value of the top property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getTop() {
        return top;
    }

    /**
     * Sets the value of the top property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTop(double value) {
        this.top = value;
    }

    public boolean isSetTop() {
        return (this.top!= null);
    }

    public void unsetTop() {
        this.top = null;
    }

}
