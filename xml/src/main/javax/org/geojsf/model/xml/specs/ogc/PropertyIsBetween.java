
package org.geojsf.model.xml.specs.ogc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyName"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}LowerBoundary"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}UpperBoundary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "propertyName",
    "lowerBoundary",
    "upperBoundary"
})
@XmlRootElement(name = "PropertyIsBetween")
public class PropertyIsBetween
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "PropertyName", required = true)
    protected PropertyName propertyName;
    @XmlElement(name = "LowerBoundary", required = true)
    protected LowerBoundary lowerBoundary;
    @XmlElement(name = "UpperBoundary", required = true)
    protected UpperBoundary upperBoundary;

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

    /**
     * Gets the value of the lowerBoundary property.
     * 
     * @return
     *     possible object is
     *     {@link LowerBoundary }
     *     
     */
    public LowerBoundary getLowerBoundary() {
        return lowerBoundary;
    }

    /**
     * Sets the value of the lowerBoundary property.
     * 
     * @param value
     *     allowed object is
     *     {@link LowerBoundary }
     *     
     */
    public void setLowerBoundary(LowerBoundary value) {
        this.lowerBoundary = value;
    }

    /**
     * Gets the value of the upperBoundary property.
     * 
     * @return
     *     possible object is
     *     {@link UpperBoundary }
     *     
     */
    public UpperBoundary getUpperBoundary() {
        return upperBoundary;
    }

    /**
     * Sets the value of the upperBoundary property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpperBoundary }
     *     
     */
    public void setUpperBoundary(UpperBoundary value) {
        this.upperBoundary = value;
    }

}
