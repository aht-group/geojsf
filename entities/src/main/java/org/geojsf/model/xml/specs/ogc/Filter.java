
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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/ogc}DWithin"/>
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsBetween"/>
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsEqualTo"/>
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
    "dWithin",
    "propertyIsBetween",
    "propertyIsEqualTo"
})
@XmlRootElement(name = "Filter")
public class Filter
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DWithin", required = true)
    protected DWithin dWithin;
    @XmlElement(name = "PropertyIsBetween", required = true)
    protected PropertyIsBetween propertyIsBetween;
    @XmlElement(name = "PropertyIsEqualTo", required = true)
    protected PropertyIsEqualTo propertyIsEqualTo;

    /**
     * Gets the value of the dWithin property.
     * 
     * @return
     *     possible object is
     *     {@link DWithin }
     *     
     */
    public DWithin getDWithin() {
        return dWithin;
    }

    /**
     * Sets the value of the dWithin property.
     * 
     * @param value
     *     allowed object is
     *     {@link DWithin }
     *     
     */
    public void setDWithin(DWithin value) {
        this.dWithin = value;
    }

    public boolean isSetDWithin() {
        return (this.dWithin!= null);
    }

    /**
     * Gets the value of the propertyIsBetween property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyIsBetween }
     *     
     */
    public PropertyIsBetween getPropertyIsBetween() {
        return propertyIsBetween;
    }

    /**
     * Sets the value of the propertyIsBetween property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyIsBetween }
     *     
     */
    public void setPropertyIsBetween(PropertyIsBetween value) {
        this.propertyIsBetween = value;
    }

    public boolean isSetPropertyIsBetween() {
        return (this.propertyIsBetween!= null);
    }

    /**
     * Gets the value of the propertyIsEqualTo property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyIsEqualTo }
     *     
     */
    public PropertyIsEqualTo getPropertyIsEqualTo() {
        return propertyIsEqualTo;
    }

    /**
     * Sets the value of the propertyIsEqualTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyIsEqualTo }
     *     
     */
    public void setPropertyIsEqualTo(PropertyIsEqualTo value) {
        this.propertyIsEqualTo = value;
    }

    public boolean isSetPropertyIsEqualTo() {
        return (this.propertyIsEqualTo!= null);
    }

}
