package net.sf.geojsf.demo.model.util.security;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.qualifier.EjbErNode;
import net.sf.geojsf.demo.model.user.GeoUser;
import net.sf.geojsf.demo.model.util.GeoDescription;
import net.sf.geojsf.demo.model.util.GeoLang;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@EjbErNode(name="SecurityAction")

public class SecurityAction implements EjbWithCode,Serializable,EjbRemoveable,EjbPersistable,
	UtilsSecurityAction<GeoLang,GeoDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,GeoUser>
{
	public static final long serialVersionUID=1;

	public static enum Code {login}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull @ManyToOne
	private SecurityView view;
		
	@NotNull
	private String code;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, GeoLang> name;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, GeoDescription> description;
	
	//******************************************************************************
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public SecurityView getView() {return view;}
	public void setView(SecurityView view) {this.view = view;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	public Map<String, GeoLang> getName() {return name;}
	public void setName(Map<String, GeoLang> name) {this.name = name;}
	
	public Map<String, GeoDescription> getDescription() {return description;}
	public void setDescription(Map<String, GeoDescription> description) {this.description = description;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof SecurityAction)
             ? id == ((SecurityAction) object).getId()
             : (object == this);
    }
}