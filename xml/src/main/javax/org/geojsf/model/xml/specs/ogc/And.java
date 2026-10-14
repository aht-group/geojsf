
package org.geojsf.model.xml.specs.ogc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsBetween" maxOccurs="5"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsEqualTo" maxOccurs="5"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsLessThan" maxOccurs="5"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsGreaterThanOrEqualTo" maxOccurs="5"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}PropertyIsGreaterThan" maxOccurs="5"/&gt;
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
@XmlRootElement(name = "And")
public class And
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DWithin", required = true)
    protected DWithin dWithin;
    @XmlElement(name = "PropertyIsBetween", required = true)
    protected List<PropertyIsBetween> propertyIsBetween;
    @XmlElement(name = "PropertyIsEqualTo", required = true)
    protected List<PropertyIsEqualTo> propertyIsEqualTo;
    @XmlElement(name = "PropertyIsLessThan", required = true)
    protected List<PropertyIsLessThan> propertyIsLessThan;
    @XmlElement(name = "PropertyIsGreaterThanOrEqualTo", required = true)
    protected List<PropertyIsGreaterThanOrEqualTo> propertyIsGreaterThanOrEqualTo;
    @XmlElement(name = "PropertyIsGreaterThan", required = true)
    protected List<PropertyIsGreaterThan> propertyIsGreaterThan;

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

    /**
     * Gets the value of the propertyIsBetween property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyIsBetween property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyIsBetween().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyIsBetween }
     * 
     * 
     */
    public List<PropertyIsBetween> getPropertyIsBetween() {
        if (propertyIsBetween == null) {
            propertyIsBetween = new ArrayList<PropertyIsBetween>();
        }
        return this.propertyIsBetween;
    }

    /**
     * Gets the value of the propertyIsEqualTo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyIsEqualTo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyIsEqualTo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyIsEqualTo }
     * 
     * 
     */
    public List<PropertyIsEqualTo> getPropertyIsEqualTo() {
        if (propertyIsEqualTo == null) {
            propertyIsEqualTo = new ArrayList<PropertyIsEqualTo>();
        }
        return this.propertyIsEqualTo;
    }

    /**
     * Gets the value of the propertyIsLessThan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyIsLessThan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyIsLessThan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyIsLessThan }
     * 
     * 
     */
    public List<PropertyIsLessThan> getPropertyIsLessThan() {
        if (propertyIsLessThan == null) {
            propertyIsLessThan = new ArrayList<PropertyIsLessThan>();
        }
        return this.propertyIsLessThan;
    }

    /**
     * Gets the value of the propertyIsGreaterThanOrEqualTo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyIsGreaterThanOrEqualTo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyIsGreaterThanOrEqualTo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyIsGreaterThanOrEqualTo }
     * 
     * 
     */
    public List<PropertyIsGreaterThanOrEqualTo> getPropertyIsGreaterThanOrEqualTo() {
        if (propertyIsGreaterThanOrEqualTo == null) {
            propertyIsGreaterThanOrEqualTo = new ArrayList<PropertyIsGreaterThanOrEqualTo>();
        }
        return this.propertyIsGreaterThanOrEqualTo;
    }

    /**
     * Gets the value of the propertyIsGreaterThan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyIsGreaterThan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyIsGreaterThan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyIsGreaterThan }
     * 
     * 
     */
    public List<PropertyIsGreaterThan> getPropertyIsGreaterThan() {
        if (propertyIsGreaterThan == null) {
            propertyIsGreaterThan = new ArrayList<PropertyIsGreaterThan>();
        }
        return this.propertyIsGreaterThan;
    }

}
