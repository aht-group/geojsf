
package org.geojsf.xml.openlayers;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://geojsf.sf.net/openlayers}layers"/>
 *         &lt;element ref="{http://geojsf.sf.net/openlayers}views"/>
 *         &lt;element ref="{http://geojsf.sf.net/openlayers}service" maxOccurs="unbounded"/>
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
    "layers",
    "views",
    "service"
})
@XmlRootElement(name = "repository")
public class Repository
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Layers layers;
    @XmlElement(required = true)
    protected Views views;
    @XmlElement(required = true)
    protected List<Service> service;

    /**
     * Gets the value of the layers property.
     * 
     * @return
     *     possible object is
     *     {@link Layers }
     *     
     */
    public Layers getLayers() {
        return layers;
    }

    /**
     * Sets the value of the layers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Layers }
     *     
     */
    public void setLayers(Layers value) {
        this.layers = value;
    }

    public boolean isSetLayers() {
        return (this.layers!= null);
    }

    /**
     * Gets the value of the views property.
     * 
     * @return
     *     possible object is
     *     {@link Views }
     *     
     */
    public Views getViews() {
        return views;
    }

    /**
     * Sets the value of the views property.
     * 
     * @param value
     *     allowed object is
     *     {@link Views }
     *     
     */
    public void setViews(Views value) {
        this.views = value;
    }

    public boolean isSetViews() {
        return (this.views!= null);
    }

    /**
     * Gets the value of the service property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the service property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Service }
     * 
     * 
     */
    public List<Service> getService() {
        if (service == null) {
            service = new ArrayList<Service>();
        }
        return this.service;
    }

    public boolean isSetService() {
        return ((this.service!= null)&&(!this.service.isEmpty()));
    }

    public void unsetService() {
        this.service = null;
    }

}