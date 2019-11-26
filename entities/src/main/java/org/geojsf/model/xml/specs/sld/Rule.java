
package org.geojsf.model.xml.specs.sld;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.geojsf.model.xml.specs.ogc.Filter;


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
 *         &lt;element ref="{http://www.opengis.net/sld}Name"/&gt;
 *         &lt;element ref="{http://www.opengis.net/sld}Description"/&gt;
 *         &lt;element ref="{http://www.opengis.net/ogc}Filter"/&gt;
 *         &lt;element ref="{http://www.opengis.net/sld}PointSymbolizer"/&gt;
 *         &lt;element ref="{http://www.opengis.net/sld}PolygonSymbolizer"/&gt;
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
    "name",
    "description",
    "filter",
    "pointSymbolizer",
    "polygonSymbolizer"
})
@XmlRootElement(name = "Rule")
public class Rule
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Name", required = true)
    protected Name name;
    @XmlElement(name = "Description", required = true)
    protected Description description;
    @XmlElement(name = "Filter", namespace = "http://www.opengis.net/ogc", required = true)
    protected Filter filter;
    @XmlElement(name = "PointSymbolizer", required = true)
    protected PointSymbolizer pointSymbolizer;
    @XmlElement(name = "PolygonSymbolizer", required = true)
    protected PolygonSymbolizer polygonSymbolizer;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setName(Name value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
    }

    public boolean isSetDescription() {
        return (this.description!= null);
    }

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link Filter }
     *     
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter }
     *     
     */
    public void setFilter(Filter value) {
        this.filter = value;
    }

    public boolean isSetFilter() {
        return (this.filter!= null);
    }

    /**
     * Gets the value of the pointSymbolizer property.
     * 
     * @return
     *     possible object is
     *     {@link PointSymbolizer }
     *     
     */
    public PointSymbolizer getPointSymbolizer() {
        return pointSymbolizer;
    }

    /**
     * Sets the value of the pointSymbolizer property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointSymbolizer }
     *     
     */
    public void setPointSymbolizer(PointSymbolizer value) {
        this.pointSymbolizer = value;
    }

    public boolean isSetPointSymbolizer() {
        return (this.pointSymbolizer!= null);
    }

    /**
     * Gets the value of the polygonSymbolizer property.
     * 
     * @return
     *     possible object is
     *     {@link PolygonSymbolizer }
     *     
     */
    public PolygonSymbolizer getPolygonSymbolizer() {
        return polygonSymbolizer;
    }

    /**
     * Sets the value of the polygonSymbolizer property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolygonSymbolizer }
     *     
     */
    public void setPolygonSymbolizer(PolygonSymbolizer value) {
        this.polygonSymbolizer = value;
    }

    public boolean isSetPolygonSymbolizer() {
        return (this.polygonSymbolizer!= null);
    }

}
