
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
 *         &lt;element ref="{http://www.opengis.net/sld}WellKnownName"/&gt;
 *         &lt;element ref="{http://www.opengis.net/sld}Fill"/&gt;
 *         &lt;element ref="{http://www.opengis.net/sld}Stroke"/&gt;
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
    "wellKnownName",
    "fill",
    "stroke"
})
@XmlRootElement(name = "Mark")
public class Mark
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "WellKnownName", required = true)
    protected WellKnownName wellKnownName;
    @XmlElement(name = "Fill", required = true)
    protected Fill fill;
    @XmlElement(name = "Stroke", required = true)
    protected Stroke stroke;

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

    /**
     * Gets the value of the fill property.
     * 
     * @return
     *     possible object is
     *     {@link Fill }
     *     
     */
    public Fill getFill() {
        return fill;
    }

    /**
     * Sets the value of the fill property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fill }
     *     
     */
    public void setFill(Fill value) {
        this.fill = value;
    }

    public boolean isSetFill() {
        return (this.fill!= null);
    }

    /**
     * Gets the value of the stroke property.
     * 
     * @return
     *     possible object is
     *     {@link Stroke }
     *     
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * Sets the value of the stroke property.
     * 
     * @param value
     *     allowed object is
     *     {@link Stroke }
     *     
     */
    public void setStroke(Stroke value) {
        this.stroke = value;
    }

    public boolean isSetStroke() {
        return (this.stroke!= null);
    }

}
