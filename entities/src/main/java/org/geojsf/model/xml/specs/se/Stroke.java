
package org.geojsf.model.xml.specs.se;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://www.opengis.net/se}SvgParameter" maxOccurs="unbounded"/&gt;
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
    "svgParameter"
})
@XmlRootElement(name = "Stroke")
public class Stroke
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "SvgParameter", required = true)
    protected List<SvgParameter> svgParameter;

    /**
     * Gets the value of the svgParameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the svgParameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSvgParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SvgParameter }
     * 
     * 
     */
    public List<SvgParameter> getSvgParameter() {
        if (svgParameter == null) {
            svgParameter = new ArrayList<SvgParameter>();
        }
        return this.svgParameter;
    }

    public boolean isSetSvgParameter() {
        return ((this.svgParameter!= null)&&(!this.svgParameter.isEmpty()));
    }

    public void unsetSvgParameter() {
        this.svgParameter = null;
    }

}
