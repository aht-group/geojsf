
package org.geojsf.model.xml.specs.sld;

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
 *         &lt;element ref="{http://www.opengis.net/sld}NamedLayer"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "namedLayer"
})
@XmlRootElement(name = "StyledLayerDescriptor")
public class StyledLayerDescriptor
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "NamedLayer", required = true)
    protected NamedLayer namedLayer;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the namedLayer property.
     * 
     * @return
     *     possible object is
     *     {@link NamedLayer }
     *     
     */
    public NamedLayer getNamedLayer() {
        return namedLayer;
    }

    /**
     * Sets the value of the namedLayer property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedLayer }
     *     
     */
    public void setNamedLayer(NamedLayer value) {
        this.namedLayer = value;
    }

    public boolean isSetNamedLayer() {
        return (this.namedLayer!= null);
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    public boolean isSetVersion() {
        return (this.version!= null);
    }

}
