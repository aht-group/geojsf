
package org.geojsf.xml.geoserver;

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
 *         &lt;element ref="{http://www.geojsf.org/geoserver}spatial"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="url" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="charset" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="memoryBuffer" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="cacheMemoryMaps" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "spatial"
})
@XmlRootElement(name = "shapeDir")
public class ShapeDir
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Spatial spatial;
    @XmlAttribute(name = "url")
    protected String url;
    @XmlAttribute(name = "charset")
    protected String charset;
    @XmlAttribute(name = "memoryBuffer")
    protected Boolean memoryBuffer;
    @XmlAttribute(name = "cacheMemoryMaps")
    protected Boolean cacheMemoryMaps;

    /**
     * Gets the value of the spatial property.
     * 
     * @return
     *     possible object is
     *     {@link Spatial }
     *     
     */
    public Spatial getSpatial() {
        return spatial;
    }

    /**
     * Sets the value of the spatial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Spatial }
     *     
     */
    public void setSpatial(Spatial value) {
        this.spatial = value;
    }

    public boolean isSetSpatial() {
        return (this.spatial!= null);
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    public boolean isSetUrl() {
        return (this.url!= null);
    }

    /**
     * Gets the value of the charset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the value of the charset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharset(String value) {
        this.charset = value;
    }

    public boolean isSetCharset() {
        return (this.charset!= null);
    }

    /**
     * Gets the value of the memoryBuffer property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isMemoryBuffer() {
        return memoryBuffer;
    }

    /**
     * Sets the value of the memoryBuffer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMemoryBuffer(boolean value) {
        this.memoryBuffer = value;
    }

    public boolean isSetMemoryBuffer() {
        return (this.memoryBuffer!= null);
    }

    public void unsetMemoryBuffer() {
        this.memoryBuffer = null;
    }

    /**
     * Gets the value of the cacheMemoryMaps property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isCacheMemoryMaps() {
        return cacheMemoryMaps;
    }

    /**
     * Sets the value of the cacheMemoryMaps property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCacheMemoryMaps(boolean value) {
        this.cacheMemoryMaps = value;
    }

    public boolean isSetCacheMemoryMaps() {
        return (this.cacheMemoryMaps!= null);
    }

    public void unsetCacheMemoryMaps() {
        this.cacheMemoryMaps = null;
    }

}
