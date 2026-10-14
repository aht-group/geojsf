
package org.geojsf.model.xml.geojsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.geojsf.org}sldTemplate"/&gt;
 *         &lt;element ref="{http://www.geojsf.org}sldRule" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sldTemplate",
    "sldRule"
})
@XmlRootElement(name = "sld")
public class Sld
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected SldTemplate sldTemplate;
    @XmlElement(required = true)
    protected List<SldRule> sldRule;
    @XmlAttribute(name = "id")
    protected Long id;

    /**
     * Gets the value of the sldTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link SldTemplate }
     *     
     */
    public SldTemplate getSldTemplate() {
        return sldTemplate;
    }

    /**
     * Sets the value of the sldTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link SldTemplate }
     *     
     */
    public void setSldTemplate(SldTemplate value) {
        this.sldTemplate = value;
    }

    /**
     * Gets the value of the sldRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sldRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSldRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SldRule }
     * 
     * 
     */
    public List<SldRule> getSldRule() {
        if (sldRule == null) {
            sldRule = new ArrayList<SldRule>();
        }
        return this.sldRule;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
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
    public void setId(Long value) {
        this.id = value;
    }

}
