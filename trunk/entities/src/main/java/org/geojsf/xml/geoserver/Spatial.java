
package org.geojsf.xml.geoserver;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="createIndex" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="enableIndex" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "spatial")
public class Spatial
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "createIndex")
    protected Boolean createIndex;
    @XmlAttribute(name = "enableIndex")
    protected Boolean enableIndex;

    /**
     * Gets the value of the createIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isCreateIndex() {
        return createIndex;
    }

    /**
     * Sets the value of the createIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCreateIndex(boolean value) {
        this.createIndex = value;
    }

    public boolean isSetCreateIndex() {
        return (this.createIndex!= null);
    }

    public void unsetCreateIndex() {
        this.createIndex = null;
    }

    /**
     * Gets the value of the enableIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEnableIndex() {
        return enableIndex;
    }

    /**
     * Sets the value of the enableIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableIndex(boolean value) {
        this.enableIndex = value;
    }

    public boolean isSetEnableIndex() {
        return (this.enableIndex!= null);
    }

    public void unsetEnableIndex() {
        this.enableIndex = null;
    }

}
