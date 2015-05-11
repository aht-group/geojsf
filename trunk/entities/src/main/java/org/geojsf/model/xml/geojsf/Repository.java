
package org.geojsf.model.xml.geojsf;

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
 *         &lt;element ref="{http://www.geojsf.org}layers"/>
 *         &lt;element ref="{http://www.geojsf.org}maps"/>
 *         &lt;element ref="{http://www.geojsf.org}service" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.geojsf.org}category" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.geojsf.org}sldTemplate" maxOccurs="unbounded"/>
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
    "maps",
    "service",
    "category",
    "sldTemplate"
})
@XmlRootElement(name = "repository")
public class Repository
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Layers layers;
    @XmlElement(required = true)
    protected Maps maps;
    @XmlElement(required = true)
    protected List<Service> service;
    @XmlElement(required = true)
    protected List<Category> category;
    @XmlElement(required = true)
    protected List<SldTemplate> sldTemplate;

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
     * Gets the value of the maps property.
     * 
     * @return
     *     possible object is
     *     {@link Maps }
     *     
     */
    public Maps getMaps() {
        return maps;
    }

    /**
     * Sets the value of the maps property.
     * 
     * @param value
     *     allowed object is
     *     {@link Maps }
     *     
     */
    public void setMaps(Maps value) {
        this.maps = value;
    }

    public boolean isSetMaps() {
        return (this.maps!= null);
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

    /**
     * Gets the value of the category property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the category property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Category }
     * 
     * 
     */
    public List<Category> getCategory() {
        if (category == null) {
            category = new ArrayList<Category>();
        }
        return this.category;
    }

    public boolean isSetCategory() {
        return ((this.category!= null)&&(!this.category.isEmpty()));
    }

    public void unsetCategory() {
        this.category = null;
    }

    /**
     * Gets the value of the sldTemplate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sldTemplate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSldTemplate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SldTemplate }
     * 
     * 
     */
    public List<SldTemplate> getSldTemplate() {
        if (sldTemplate == null) {
            sldTemplate = new ArrayList<SldTemplate>();
        }
        return this.sldTemplate;
    }

    public boolean isSetSldTemplate() {
        return ((this.sldTemplate!= null)&&(!this.sldTemplate.isEmpty()));
    }

    public void unsetSldTemplate() {
        this.sldTemplate = null;
    }

}
