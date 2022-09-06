
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
 *         &lt;element ref="{http://www.opengis.net/sld}Mark"/&gt;
 *         &lt;element ref="{http://www.opengis.net/sld}ExternalGraphic"/&gt;
 *         &lt;element ref="{http://www.opengis.net/sld}Size"/&gt;
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
    "mark",
    "externalGraphic",
    "size"
})
@XmlRootElement(name = "Graphic")
public class Graphic
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Mark", required = true)
    protected Mark mark;
    @XmlElement(name = "ExternalGraphic", required = true)
    protected ExternalGraphic externalGraphic;
    @XmlElement(name = "Size", required = true)
    protected Size size;

    /**
     * Gets the value of the mark property.
     * 
     * @return
     *     possible object is
     *     {@link Mark }
     *     
     */
    public Mark getMark() {
        return mark;
    }

    /**
     * Sets the value of the mark property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mark }
     *     
     */
    public void setMark(Mark value) {
        this.mark = value;
    }

    public boolean isSetMark() {
        return (this.mark!= null);
    }

    /**
     * Gets the value of the externalGraphic property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalGraphic }
     *     
     */
    public ExternalGraphic getExternalGraphic() {
        return externalGraphic;
    }

    /**
     * Sets the value of the externalGraphic property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalGraphic }
     *     
     */
    public void setExternalGraphic(ExternalGraphic value) {
        this.externalGraphic = value;
    }

    public boolean isSetExternalGraphic() {
        return (this.externalGraphic!= null);
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Size }
     *     
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Size }
     *     
     */
    public void setSize(Size value) {
        this.size = value;
    }

    public boolean isSetSize() {
        return (this.size!= null);
    }

}
