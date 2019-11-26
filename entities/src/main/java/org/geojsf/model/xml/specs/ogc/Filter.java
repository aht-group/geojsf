
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
 *         &lt;element ref="{http://www.opengis.net/ogc}DWithin"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsBetween"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsEqualTo"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsLessThan"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsGreaterThanOrEqualTo"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsGreaterThan"/&gt;
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
    "dWithin",
    "propertyIsBetween",
    "propertyIsEqualTo",
    "propertyIsLessThan",
    "propertyIsGreaterThanOrEqualTo",
    "propertyIsGreaterThan"
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
    @XmlElement(name = "PropertyIsLessThan", required = true)
    protected PropertyIsLessThan propertyIsLessThan;
    @XmlElement(name = "PropertyIsGreaterThanOrEqualTo", required = true)
    protected PropertyIsGreaterThanOrEqualTo propertyIsGreaterThanOrEqualTo;
    @XmlElement(name = "PropertyIsGreaterThan", required = true)
    protected PropertyIsGreaterThan propertyIsGreaterThan;

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

    /**
     * Gets the value of the propertyIsLessThan property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyIsLessThan }
     *     
     */
    public PropertyIsLessThan getPropertyIsLessThan() {
        return propertyIsLessThan;
    }

    /**
     * Sets the value of the propertyIsLessThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyIsLessThan }
     *     
     */
    public void setPropertyIsLessThan(PropertyIsLessThan value) {
        this.propertyIsLessThan = value;
    }

    public boolean isSetPropertyIsLessThan() {
        return (this.propertyIsLessThan!= null);
    }

    /**
     * Gets the value of the propertyIsGreaterThanOrEqualTo property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyIsGreaterThanOrEqualTo }
     *     
     */
    public PropertyIsGreaterThanOrEqualTo getPropertyIsGreaterThanOrEqualTo() {
        return propertyIsGreaterThanOrEqualTo;
    }

    /**
     * Sets the value of the propertyIsGreaterThanOrEqualTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyIsGreaterThanOrEqualTo }
     *     
     */
    public void setPropertyIsGreaterThanOrEqualTo(PropertyIsGreaterThanOrEqualTo value) {
        this.propertyIsGreaterThanOrEqualTo = value;
    }

    public boolean isSetPropertyIsGreaterThanOrEqualTo() {
        return (this.propertyIsGreaterThanOrEqualTo!= null);
    }

    /**
     * Gets the value of the propertyIsGreaterThan property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyIsGreaterThan }
     *     
     */
    public PropertyIsGreaterThan getPropertyIsGreaterThan() {
        return propertyIsGreaterThan;
    }

    /**
     * Sets the value of the propertyIsGreaterThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyIsGreaterThan }
     *     
     */
    public void setPropertyIsGreaterThan(PropertyIsGreaterThan value) {
        this.propertyIsGreaterThan = value;
    }

    public boolean isSetPropertyIsGreaterThan() {
        return (this.propertyIsGreaterThan!= null);
    }

}
