
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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/sld}NamedLayer"/>
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
    "namedLayer"
})
@XmlRootElement(name = "StyledLayerDescriptor")
public class StyledLayerDescriptor
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "NamedLayer", required = true)
    protected NamedLayer namedLayer;

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

}
