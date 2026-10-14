
package org.geojsf.model.xml.geoserver;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.exlp.model.xml.net.Database;
import org.exlp.model.xml.net.Host;


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
 *         &lt;element ref="{http://exlp.sf.net/net}host"/&gt;
 *         &lt;element ref="{http://exlp.sf.net/net}database"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="min" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="fetchSize" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="preparedStatements" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="maxPreparedStatements" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="validate" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="looseBbox" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="encodeFunctions" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="exposePrimaryKeys" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="estimatedExtends" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
    @XmlAttribute(name = "looseBbox")
    protected Boolean looseBbox;
    @XmlAttribute(name = "encodeFunctions")
    protected Boolean encodeFunctions;
    @XmlAttribute(name = "exposePrimaryKeys")
    protected Boolean exposePrimaryKeys;
    @XmlAttribute(name = "estimatedExtends")
    protected Boolean estimatedExtends;

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

    /**
     * Gets the value of the timeout property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeout() {
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
    public void setTimeout(Integer value) {
        this.timeout = value;
    }

    /**
     * Gets the value of the min property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMin() {
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
    public void setMin(Integer value) {
        this.min = value;
    }

    /**
     * Gets the value of the max property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMax() {
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
    public void setMax(Integer value) {
        this.max = value;
    }

    /**
     * Gets the value of the fetchSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFetchSize() {
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
    public void setFetchSize(Integer value) {
        this.fetchSize = value;
    }

    /**
     * Gets the value of the preparedStatements property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreparedStatements() {
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
    public void setPreparedStatements(Boolean value) {
        this.preparedStatements = value;
    }

    /**
     * Gets the value of the maxPreparedStatements property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxPreparedStatements() {
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
    public void setMaxPreparedStatements(Integer value) {
        this.maxPreparedStatements = value;
    }

    /**
     * Gets the value of the validate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidate() {
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
    public void setValidate(Boolean value) {
        this.validate = value;
    }

    /**
     * Gets the value of the looseBbox property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLooseBbox() {
        return looseBbox;
    }

    /**
     * Sets the value of the looseBbox property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLooseBbox(Boolean value) {
        this.looseBbox = value;
    }

    /**
     * Gets the value of the encodeFunctions property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEncodeFunctions() {
        return encodeFunctions;
    }

    /**
     * Sets the value of the encodeFunctions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEncodeFunctions(Boolean value) {
        this.encodeFunctions = value;
    }

    /**
     * Gets the value of the exposePrimaryKeys property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExposePrimaryKeys() {
        return exposePrimaryKeys;
    }

    /**
     * Sets the value of the exposePrimaryKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExposePrimaryKeys(Boolean value) {
        this.exposePrimaryKeys = value;
    }

    /**
     * Gets the value of the estimatedExtends property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEstimatedExtends() {
        return estimatedExtends;
    }

    /**
     * Sets the value of the estimatedExtends property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEstimatedExtends(Boolean value) {
        this.estimatedExtends = value;
    }

}
