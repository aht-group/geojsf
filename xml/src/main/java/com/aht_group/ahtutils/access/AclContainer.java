
package com.aht_group.ahtutils.access;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://ahtutils.aht-group.com/access}roleAutoAssign" maxOccurs="unbounded"/>
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
    "roleAutoAssign"
})
@XmlRootElement(name = "aclContainer")
public class AclContainer
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<RoleAutoAssign> roleAutoAssign;

    /**
     * Gets the value of the roleAutoAssign property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleAutoAssign property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleAutoAssign().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RoleAutoAssign }
     * 
     * 
     */
    public List<RoleAutoAssign> getRoleAutoAssign() {
        if (roleAutoAssign == null) {
            roleAutoAssign = new ArrayList<RoleAutoAssign>();
        }
        return this.roleAutoAssign;
    }

    public boolean isSetRoleAutoAssign() {
        return ((this.roleAutoAssign!= null)&&(!this.roleAutoAssign.isEmpty()));
    }

    public void unsetRoleAutoAssign() {
        this.roleAutoAssign = null;
    }

}