
package org.geojsf.xml.geoserver;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.exlp.xml.net.Database;
import net.sf.exlp.xml.net.Host;


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
 *         &lt;element ref="{http://exlp.sf.net/net}host"/>
 *         &lt;element ref="{http://exlp.sf.net/net}database"/>
 *       &lt;/sequence>
 *       &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="min" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="fetchSize" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="preparedStatements" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="maxPreparedStatements" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="validate" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "host",
    "database"
})
@XmlRootElement(name = "connection")
public class Connection
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://exlp.sf.net/net", required = true)
    protected Host host;
    @XmlElement(namespace = "http://exlp.sf.net/net", required = true)
    protected Database database;
    @XmlAttribute(name = "timeout")
    protected Integer timeout;
    @XmlAttribute(name = "min")
    protected Integer min;
    @XmlAttribute(name = "max")
    protected Integer max;
    @XmlAttribute(name = "fetchSize")
    protected Integer fetchSize;
    @XmlAttribute(name = "preparedStatements")
    protected Boolean preparedStatements;
    @XmlAttribute(name = "maxPreparedStatements")
    protected Integer maxPreparedStatements;
    @XmlAttribute(name = "validate")
    protected Boolean validate;

    /**
     * Gets the value of the host property.
     * 
     * @return
     *     possible object is
     *     {@link Host }
     *     
     */
    public Host getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     * 
     * @param value
     *     allowed object is
     *     {@link Host }
     *     
     */
    public void setHost(Host value) {
        this.host = value;
    }

    public boolean isSetHost() {
        return (this.host!= null);
    }

    /**
     * Gets the value of the database property.
     * 
     * @return
     *     possible object is
     *     {@link Database }
     *     
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * Sets the value of the database property.
     * 
     * @param value
     *     allowed object is
     *     {@link Database }
     *     
     */
    public void setDatabase(Database value) {
        this.database = value;
    }

    public boolean isSetDatabase() {
        return (this.database!= null);
    }

    /**
     * Gets the value of the timeout property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets the value of the timeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeout(int value) {
        this.timeout = value;
    }

    public boolean isSetTimeout() {
        return (this.timeout!= null);
    }

    public void unsetTimeout() {
        this.timeout = null;
    }

    /**
     * Gets the value of the min property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the value of the min property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMin(int value) {
        this.min = value;
    }

    public boolean isSetMin() {
        return (this.min!= null);
    }

    public void unsetMin() {
        this.min = null;
    }

    /**
     * Gets the value of the max property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the value of the max property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMax(int value) {
        this.max = value;
    }

    public boolean isSetMax() {
        return (this.max!= null);
    }

    public void unsetMax() {
        this.max = null;
    }

    /**
     * Gets the value of the fetchSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getFetchSize() {
        return fetchSize;
    }

    /**
     * Sets the value of the fetchSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFetchSize(int value) {
        this.fetchSize = value;
    }

    public boolean isSetFetchSize() {
        return (this.fetchSize!= null);
    }

    public void unsetFetchSize() {
        this.fetchSize = null;
    }

    /**
     * Gets the value of the preparedStatements property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPreparedStatements() {
        return preparedStatements;
    }

    /**
     * Sets the value of the preparedStatements property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreparedStatements(boolean value) {
        this.preparedStatements = value;
    }

    public boolean isSetPreparedStatements() {
        return (this.preparedStatements!= null);
    }

    public void unsetPreparedStatements() {
        this.preparedStatements = null;
    }

    /**
     * Gets the value of the maxPreparedStatements property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getMaxPreparedStatements() {
        return maxPreparedStatements;
    }

    /**
     * Sets the value of the maxPreparedStatements property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxPreparedStatements(int value) {
        this.maxPreparedStatements = value;
    }

    public boolean isSetMaxPreparedStatements() {
        return (this.maxPreparedStatements!= null);
    }

    public void unsetMaxPreparedStatements() {
        this.maxPreparedStatements = null;
    }

    /**
     * Gets the value of the validate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isValidate() {
        return validate;
    }

    /**
     * Sets the value of the validate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidate(boolean value) {
        this.validate = value;
    }

    public boolean isSetValidate() {
        return (this.validate!= null);
    }

    public void unsetValidate() {
        this.validate = null;
    }

}
