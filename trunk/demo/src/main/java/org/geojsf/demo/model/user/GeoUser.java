package org.geojsf.demo.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.geojsf.demo.model.util.GeoDescription;
import org.geojsf.demo.model.util.GeoLang;
import org.geojsf.demo.model.util.security.SecurityAction;
import org.geojsf.demo.model.util.security.SecurityCategory;
import org.geojsf.demo.model.util.security.SecurityRole;
import org.geojsf.demo.model.util.security.SecurityUsecase;
import org.geojsf.demo.model.util.security.SecurityView;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(name = "User")
@EjbErNode(name="User")
public class GeoUser implements Serializable,EjbWithId,EjbPersistable,
								UtilsUser<GeoLang,GeoDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,GeoUser>
{
	public static final long serialVersionUID=2;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
	
	@NotNull @Column(unique=true)
	protected String email;
	
	protected String password;
	protected String firstName,lastName;
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="users")
	private List<SecurityRole> roles;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<	
		
	public long getId() {return id;}
	public void setId(long id){this.id = id;}
	
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	public List<SecurityRole> getRoles() {if(roles==null){roles = new ArrayList<SecurityRole>();};return roles;}
	public void setRoles(List<SecurityRole> roles) {this.roles = roles;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof GeoUser)
             ? id == ((GeoUser) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" "+lastName+", "+firstName);
			sb.append(" ("+email+")");
		return sb.toString();
	}
}