
package org.geojsf.model.xml.specs.sld;

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
 *         &lt;element ref="{http://www.opengis.net/sld}OnlineResource"/&gt;
 *         &lt;element ref="{http://www.opengis.net/sld}Format"/&gt;
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
    "onlineResource",
    "format"
})
@XmlRootElement(name = "ExternalGraphic")
public class ExternalGraphic
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "OnlineResource", required = true)
    protected OnlineResource onlineResource;
    @XmlElement(name = "Format", required = true)
    protected Format format;

    /**
     * Gets the value of the onlineResource property.
     * 
     * @return
     *     possible object is
     *     {@link OnlineResource }
     *     
     */
    public OnlineResource getOnlineResource() {
        return onlineResource;
    }

    /**
     * Sets the value of the onlineResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link OnlineResource }
     *     
     */
    public void setOnlineResource(OnlineResource value) {
        this.onlineResource = value;
    }

    public boolean isSetOnlineResource() {
        return (this.onlineResource!= null);
    }

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link Format }
     *     
     */
    public Format getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link Format }
     *     
     */
    public void setFormat(Format value) {
        this.format = value;
    }

    public boolean isSetFormat() {
        return (this.format!= null);
    }

}
