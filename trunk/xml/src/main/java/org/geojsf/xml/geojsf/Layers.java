
package org.geojsf.xml.geojsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://www.geojsf.org}layer" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="wms" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="wcs" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "layer"
})
@XmlRootElement(name = "layers")
public class Layers
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Layer> layer;
    @XmlAttribute(name = "wms")
    protected String wms;
    @XmlAttribute(name = "wcs")
    protected String wcs;

    /**
     * Gets the value of the layer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the layer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLayer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Layer }
     * 
     * 
     */
    public List<Layer> getLayer() {
        if (layer == null) {
            layer = new ArrayList<Layer>();
        }
        return this.layer;
    }

    public boolean isSetLayer() {
        return ((this.layer!= null)&&(!this.layer.isEmpty()));
    }

    public void unsetLayer() {
        this.layer = null;
    }

    /**
     * Gets the value of the wms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWms() {
        return wms;
    }

    /**
     * Sets the value of the wms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWms(String value) {
        this.wms = value;
    }

    public boolean isSetWms() {
        return (this.wms!= null);
    }

    /**
     * Gets the value of the wcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWcs() {
        return wcs;
    }

    /**
     * Sets the value of the wcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWcs(String value) {
        this.wcs = value;
    }

    public boolean isSetWcs() {
        return (this.wcs!= null);
    }

}