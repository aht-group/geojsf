
package com.aht_group.ahtutils.navigation;

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
 *         &lt;element ref="{http://ahtutils.aht-group.com/navigation}viewPattern"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/navigation}urlMapping"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="package" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "viewPattern",
    "urlMapping"
})
@XmlRootElement(name = "navigation")
public class Navigation
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ViewPattern viewPattern;
    @XmlElement(required = true)
    protected UrlMapping urlMapping;
    @XmlAttribute(name = "code")
    protected String code;
    @XmlAttribute(name = "package")
    protected String _package;

    /**
     * Gets the value of the viewPattern property.
     * 
     * @return
     *     possible object is
     *     {@link ViewPattern }
     *     
     */
    public ViewPattern getViewPattern() {
        return viewPattern;
    }

    /**
     * Sets the value of the viewPattern property.
     * 
     * @param value
     *     allowed object is
     *     {@link ViewPattern }
     *     
     */
    public void setViewPattern(ViewPattern value) {
        this.viewPattern = value;
    }

    public boolean isSetViewPattern() {
        return (this.viewPattern!= null);
    }

    /**
     * Gets the value of the urlMapping property.
     * 
     * @return
     *     possible object is
     *     {@link UrlMapping }
     *     
     */
    public UrlMapping getUrlMapping() {
        return urlMapping;
    }

    /**
     * Sets the value of the urlMapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link UrlMapping }
     *     
     */
    public void setUrlMapping(UrlMapping value) {
        this.urlMapping = value;
    }

    public boolean isSetUrlMapping() {
        return (this.urlMapping!= null);
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    public boolean isSetCode() {
        return (this.code!= null);
    }

    /**
     * Gets the value of the package property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackage() {
        return _package;
    }

    /**
     * Sets the value of the package property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackage(String value) {
        this._package = value;
    }

    public boolean isSetPackage() {
        return (this._package!= null);
    }

}