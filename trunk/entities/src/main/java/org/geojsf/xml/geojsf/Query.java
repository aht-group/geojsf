
package org.geojsf.xml.geojsf;

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
 *         &lt;element ref="{http://www.geojsf.org}repository"/>
 *         &lt;element ref="{http://www.geojsf.org}service"/>
 *         &lt;element ref="{http://www.geojsf.org}category"/>
 *         &lt;element ref="{http://www.geojsf.org}layer"/>
 *         &lt;element ref="{http://www.geojsf.org}map"/>
 *         &lt;element ref="{http://www.geojsf.org}view"/>
 *         &lt;element ref="{http://www.geojsf.org}viewPort"/>
 *         &lt;element ref="{http://www.geojsf.org}sldTemplate"/>
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
    "repository",
    "service",
    "category",
    "layer",
    "map",
    "view",
    "viewPort",
    "sldTemplate"
})
@XmlRootElement(name = "query")
public class Query
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Repository repository;
    @XmlElement(required = true)
    protected Service service;
    @XmlElement(required = true)
    protected Category category;
    @XmlElement(required = true)
    protected Layer layer;
    @XmlElement(required = true)
    protected Map map;
    @XmlElement(required = true)
    protected View view;
    @XmlElement(required = true)
    protected ViewPort viewPort;
    @XmlElement(required = true)
    protected SldTemplate sldTemplate;

    /**
     * Gets the value of the repository property.
     * 
     * @return
     *     possible object is
     *     {@link Repository }
     *     
     */
    public Repository getRepository() {
        return repository;
    }

    /**
     * Sets the value of the repository property.
     * 
     * @param value
     *     allowed object is
     *     {@link Repository }
     *     
     */
    public void setRepository(Repository value) {
        this.repository = value;
    }

    public boolean isSetRepository() {
        return (this.repository!= null);
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link Service }
     *     
     */
    public Service getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link Service }
     *     
     */
    public void setService(Service value) {
        this.service = value;
    }

    public boolean isSetService() {
        return (this.service!= null);
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link Category }
     *     
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link Category }
     *     
     */
    public void setCategory(Category value) {
        this.category = value;
    }

    public boolean isSetCategory() {
        return (this.category!= null);
    }

    /**
     * Gets the value of the layer property.
     * 
     * @return
     *     possible object is
     *     {@link Layer }
     *     
     */
    public Layer getLayer() {
        return layer;
    }

    /**
     * Sets the value of the layer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Layer }
     *     
     */
    public void setLayer(Layer value) {
        this.layer = value;
    }

    public boolean isSetLayer() {
        return (this.layer!= null);
    }

    /**
     * Gets the value of the map property.
     * 
     * @return
     *     possible object is
     *     {@link Map }
     *     
     */
    public Map getMap() {
        return map;
    }

    /**
     * Sets the value of the map property.
     * 
     * @param value
     *     allowed object is
     *     {@link Map }
     *     
     */
    public void setMap(Map value) {
        this.map = value;
    }

    public boolean isSetMap() {
        return (this.map!= null);
    }

    /**
     * Gets the value of the view property.
     * 
     * @return
     *     possible object is
     *     {@link View }
     *     
     */
    public View getView() {
        return view;
    }

    /**
     * Sets the value of the view property.
     * 
     * @param value
     *     allowed object is
     *     {@link View }
     *     
     */
    public void setView(View value) {
        this.view = value;
    }

    public boolean isSetView() {
        return (this.view!= null);
    }

    /**
     * Gets the value of the viewPort property.
     * 
     * @return
     *     possible object is
     *     {@link ViewPort }
     *     
     */
    public ViewPort getViewPort() {
        return viewPort;
    }

    /**
     * Sets the value of the viewPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link ViewPort }
     *     
     */
    public void setViewPort(ViewPort value) {
        this.viewPort = value;
    }

    public boolean isSetViewPort() {
        return (this.viewPort!= null);
    }

    /**
     * Gets the value of the sldTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link SldTemplate }
     *     
     */
    public SldTemplate getSldTemplate() {
        return sldTemplate;
    }

    /**
     * Sets the value of the sldTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link SldTemplate }
     *     
     */
    public void setSldTemplate(SldTemplate value) {
        this.sldTemplate = value;
    }

    public boolean isSetSldTemplate() {
        return (this.sldTemplate!= null);
    }

}
