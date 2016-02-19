
package org.geojsf.model.xml.specs.se;

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
 *         &lt;element ref="{http://www.opengis.net/se}WellKnownName"/>
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
    "wellKnownName"
})
@XmlRootElement(name = "Mark")
public class Mark
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "WellKnownName", required = true)
    protected WellKnownName wellKnownName;

    /**
     * Gets the value of the wellKnownName property.
     * 
     * @return
     *     possible object is
     *     {@link WellKnownName }
     *     
     */
    public WellKnownName getWellKnownName() {
        return wellKnownName;
    }

    /**
     * Sets the value of the wellKnownName property.
     * 
     * @param value
     *     allowed object is
     *     {@link WellKnownName }
     *     
     */
    public void setWellKnownName(WellKnownName value) {
        this.wellKnownName = value;
    }

    public boolean isSetWellKnownName() {
        return (this.wellKnownName!= null);
    }

}
