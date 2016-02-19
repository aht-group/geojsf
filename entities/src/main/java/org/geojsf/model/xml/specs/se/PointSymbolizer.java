
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
 *         &lt;element ref="{http://www.opengis.net/se}Graphic"/>
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
    "graphic"
})
@XmlRootElement(name = "PointSymbolizer")
public class PointSymbolizer
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Graphic", required = true)
    protected Graphic graphic;

    /**
     * Gets the value of the graphic property.
     * 
     * @return
     *     possible object is
     *     {@link Graphic }
     *     
     */
    public Graphic getGraphic() {
        return graphic;
    }

    /**
     * Sets the value of the graphic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Graphic }
     *     
     */
    public void setGraphic(Graphic value) {
        this.graphic = value;
    }

    public boolean isSetGraphic() {
        return (this.graphic!= null);
    }

}
