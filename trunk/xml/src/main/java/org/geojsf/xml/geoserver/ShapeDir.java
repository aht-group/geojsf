
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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.geojsf.org/geoserver}spatial"/>
 *       &lt;/sequence>
 *       &lt;attribute name="url" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="charSet" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="memoryBuffer" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="cacheMemoryMaps" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fstype" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="filetype" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
    @XmlAttribute(name = "charSet")
    protected String charSet;
    @XmlAttribute(name = "memoryBuffer")
    protected Boolean memoryBuffer;
    @XmlAttribute(name = "cacheMemoryMaps")
    protected Boolean cacheMemoryMaps;
    @XmlAttribute(name = "timezone")
    protected String timezone;
    @XmlAttribute(name = "fstype")
    protected String fstype;
    @XmlAttribute(name = "filetype")
    protected String filetype;

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
     * Gets the value of the charSet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharSet() {
        return charSet;
    }

    /**
     * Sets the value of the charSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharSet(String value) {
        this.charSet = value;
    }

    public boolean isSetCharSet() {
        return (this.charSet!= null);
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

    /**
     * Gets the value of the timezone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the value of the timezone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimezone(String value) {
        this.timezone = value;
    }

    public boolean isSetTimezone() {
        return (this.timezone!= null);
    }

    /**
     * Gets the value of the fstype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFstype() {
        return fstype;
    }

    /**
     * Sets the value of the fstype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFstype(String value) {
        this.fstype = value;
    }

    public boolean isSetFstype() {
        return (this.fstype!= null);
    }

    /**
     * Gets the value of the filetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiletype() {
        return filetype;
    }

    /**
     * Sets the value of the filetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiletype(String value) {
        this.filetype = value;
    }

    public boolean isSetFiletype() {
        return (this.filetype!= null);
    }

}
