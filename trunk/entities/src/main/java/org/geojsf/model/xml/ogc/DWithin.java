
package org.geojsf.model.xml.ogc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.geojsf.xml.gml.Point;


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
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyName"/>
 *         &lt;element ref="{http://www.opengis.net/gml}Point"/>
 *         &lt;element ref="{http://www.opengis.net/ogc}Distance"/>
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
    "propertyName",
    "point",
    "distance"
})
@XmlRootElement(name = "DWithin")
public class DWithin
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "PropertyName", required = true)
    protected PropertyName propertyName;
    @XmlElement(name = "Point", namespace = "http://www.opengis.net/gml", required = true)
    protected Point point;
    @XmlElement(name = "Distance", required = true)
    protected Distance distance;

    /**
     * Gets the value of the propertyName property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyName }
     *     
     */
    public PropertyName getPropertyName() {
        return propertyName;
    }

    /**
     * Sets the value of the propertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyName }
     *     
     */
    public void setPropertyName(PropertyName value) {
        this.propertyName = value;
    }

    public boolean isSetPropertyName() {
        return (this.propertyName!= null);
    }

    /**
     * Gets the value of the point property.
     * 
     * @return
     *     possible object is
     *     {@link Point }
     *     
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Sets the value of the point property.
     * 
     * @param value
     *     allowed object is
     *     {@link Point }
     *     
     */
    public void setPoint(Point value) {
        this.point = value;
    }

    public boolean isSetPoint() {
        return (this.point!= null);
    }

    /**
     * Gets the value of the distance property.
     * 
     * @return
     *     possible object is
     *     {@link Distance }
     *     
     */
    public Distance getDistance() {
        return distance;
    }

    /**
     * Sets the value of the distance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Distance }
     *     
     */
    public void setDistance(Distance value) {
        this.distance = value;
    }

    public boolean isSetDistance() {
        return (this.distance!= null);
    }

}
