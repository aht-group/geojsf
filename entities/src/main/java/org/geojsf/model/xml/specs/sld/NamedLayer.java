
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
 *         &lt;element ref="{http://www.opengis.net/sld}UserStyle"/>
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
    "userStyle"
})
@XmlRootElement(name = "NamedLayer")
public class NamedLayer
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "UserStyle", required = true)
    protected UserStyle userStyle;

    /**
     * Gets the value of the userStyle property.
     * 
     * @return
     *     possible object is
     *     {@link UserStyle }
     *     
     */
    public UserStyle getUserStyle() {
        return userStyle;
    }

    /**
     * Sets the value of the userStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserStyle }
     *     
     */
    public void setUserStyle(UserStyle value) {
        this.userStyle = value;
    }

    public boolean isSetUserStyle() {
        return (this.userStyle!= null);
    }

}
