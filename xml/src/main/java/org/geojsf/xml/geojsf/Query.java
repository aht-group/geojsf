
package org.geojsf.xml.geojsf;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.geojsf.xml.openlayers.Layer;
import org.geojsf.xml.openlayers.Map;
import org.geojsf.xml.openlayers.Repository;
import org.geojsf.xml.openlayers.Service;


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
 *         &lt;element ref="{http://geojsf.sf.net/openlayers}repository"/>
 *         &lt;element ref="{http://geojsf.sf.net/openlayers}service"/>
 *         &lt;element ref="{http://geojsf.sf.net/openlayers}layer"/>
 *         &lt;element ref="{http://geojsf.sf.net/openlayers}map"/>
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
    "layer",
    "map"
})
@XmlRootElement(name = "query")
public class Query
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://geojsf.sf.net/openlayers", required = true)
    protected Repository repository;
    @XmlElement(namespace = "http://geojsf.sf.net/openlayers", required = true)
    protected Service service;
    @XmlElement(namespace = "http://geojsf.sf.net/openlayers", required = true)
    protected Layer layer;
    @XmlElement(namespace = "http://geojsf.sf.net/openlayers", required = true)
    protected Map map;

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

}