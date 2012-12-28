package net.sf.geojsf.demo.model.util.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.qualifier.EjbErNode;
import net.sf.geojsf.demo.model.user.GeoUser;
import net.sf.geojsf.demo.model.util.GeoDescription;
import net.sf.geojsf.demo.model.util.GeoLang;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@EjbErNode(name="SecurityRole")

public class SecurityRole implements EjbWithCode,Serializable,EjbRemoveable,EjbPersistable,
	UtilsSecurityRole<GeoLang,GeoDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,GeoUser>
{
	public static enum Code {test}	
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull @ManyToOne
	private SecurityCategory category;
	
	@NotNull
	private String code;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, GeoLang> name;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, GeoDescription> description;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<SecurityView> views;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<SecurityAction> actions;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<SecurityUsecase> usecases;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "User_SecurityRole")
	private List<GeoUser> users;
	
	//******************************************************************************
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public SecurityCategory getCategory() {return category;}
	public void setCategory(SecurityCategory category) {this.category = category;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	public Map<String, GeoLang> getName() {return name;}
	public void setName(Map<String, GeoLang> name) {this.name = name;}
	
	public Map<String, GeoDescription> getDescription() {return description;}
	public void setDescription(Map<String, GeoDescription> description) {this.description = description;}
	
	public List<SecurityView> getViews() {if(views==null){views = new ArrayList<SecurityView>();}return views;}
	public void setViews(List<SecurityView> views) {this.views = views;}
	
	public List<SecurityAction> getActions() {if(actions==null){actions = new ArrayList<SecurityAction>();}return actions;}
	public void setActions(List<SecurityAction> actions) {this.actions = actions;}
	
	public List<SecurityUsecase> getUsecases() {if(usecases==null){usecases = new ArrayList<SecurityUsecase>();}return usecases;}
	public void setUsecases(List<SecurityUsecase> usecases) {this.usecases = usecases;}
	
	public List<GeoUser> getUsers() {return users;}
	public void setUsers(List<GeoUser> users) {this.users = users;}
		
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof SecurityRole)
             ? id == ((SecurityRole) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" (").append(code).append(")");
		return sb.toString();
	}

}