
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
 *         &lt;element ref="{http://www.geojsf.org/geoserver}dataStore"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="nativeName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dataStore"
})
@XmlRootElement(name = "featureType")
public class FeatureType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected DataStore dataStore;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "nativeName")
    protected String nativeName;

    /**
     * Gets the value of the dataStore property.
     * 
     * @return
     *     possible object is
     *     {@link DataStore }
     *     
     */
    public DataStore getDataStore() {
        return dataStore;
    }

    /**
     * Sets the value of the dataStore property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataStore }
     *     
     */
    public void setDataStore(DataStore value) {
        this.dataStore = value;
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
     * Gets the value of the nativeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNativeName() {
        return nativeName;
    }

    /**
     * Sets the value of the nativeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNativeName(String value) {
        this.nativeName = value;
    }

}
