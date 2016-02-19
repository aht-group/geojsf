
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
 *         &lt;element ref="{http://www.opengis.net/se}Mark"/>
 *         &lt;element ref="{http://www.opengis.net/se}Size"/>
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
    "mark",
    "size"
})
@XmlRootElement(name = "Graphic")
public class Graphic
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Mark", required = true)
    protected Mark mark;
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
