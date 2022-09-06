
package org.geojsf.model.xml.geojsf;

import java.io.Serializable;
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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="lat" type="{http://www.w3.org/2001/XMLSchema}double" /&gt;
 *       &lt;attribute name="lon" type="{http://www.w3.org/2001/XMLSchema}double" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "coordinate")
public class Coordinate
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "lat")
    protected Double lat;
    @XmlAttribute(name = "lon")
    protected Double lon;

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

}
