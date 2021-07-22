
package org.geojsf.model.xml.geoserver;

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

    /**
     * Gets the value of the looseBbox property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isLooseBbox() {
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
    public void setLooseBbox(boolean value) {
        this.looseBbox = value;
    }

    public boolean isSetLooseBbox() {
        return (this.looseBbox!= null);
    }

    public void unsetLooseBbox() {
        this.looseBbox = null;
    }

    /**
     * Gets the value of the encodeFunctions property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEncodeFunctions() {
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
    public void setEncodeFunctions(boolean value) {
        this.encodeFunctions = value;
    }

    public boolean isSetEncodeFunctions() {
        return (this.encodeFunctions!= null);
    }

    public void unsetEncodeFunctions() {
        this.encodeFunctions = null;
    }

    /**
     * Gets the value of the exposePrimaryKeys property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isExposePrimaryKeys() {
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
    public void setExposePrimaryKeys(boolean value) {
        this.exposePrimaryKeys = value;
    }

    public boolean isSetExposePrimaryKeys() {
        return (this.exposePrimaryKeys!= null);
    }

    public void unsetExposePrimaryKeys() {
        this.exposePrimaryKeys = null;
    }

    /**
     * Gets the value of the estimatedExtends property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEstimatedExtends() {
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
    public void setEstimatedExtends(boolean value) {
        this.estimatedExtends = value;
    }

    public boolean isSetEstimatedExtends() {
        return (this.estimatedExtends!= null);
    }

    public void unsetEstimatedExtends() {
        this.estimatedExtends = null;
    }

}
