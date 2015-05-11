
package org.geojsf.model.xml.geojsf;

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
 *         &lt;element ref="{http://www.geojsf.org}sld"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sld"
})
@XmlRootElement(name = "sldRule")
public class SldRule
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Sld sld;
    @XmlAttribute(name = "id")
    protected Long id;

    /**
     * Gets the value of the sld property.
     * 
     * @return
     *     possible object is
     *     {@link Sld }
     *     
     */
    public Sld getSld() {
        return sld;
    }

    /**
     * Sets the value of the sld property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sld }
     *     
     */
    public void setSld(Sld value) {
        this.sld = value;
    }

    public boolean isSetSld() {
        return (this.sld!= null);
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(long value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    public void unsetId() {
        this.id = null;
    }

}
