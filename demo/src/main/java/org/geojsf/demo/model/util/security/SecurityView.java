package org.geojsf.demo.model.util.security;

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
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.geojsf.demo.model.user.GeoUser;
import org.geojsf.demo.model.util.GeoDescription;
import org.geojsf.demo.model.util.GeoLang;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@EjbErNode(name="SecurityView")

public class SecurityView implements EjbWithCode,Serializable,EjbRemoveable,EjbPersistable,
	UtilsSecurityView<GeoLang,GeoDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,GeoUser>
{
	public static enum Code {welcome}
	public static enum CodeTutorial {tutorial}
	
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
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="view")
	private List<SecurityAction> actions;
	
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
	
	public List<SecurityAction> getActions() {if(actions==null){actions=new ArrayList<SecurityAction>();}return actions;}
	public void setActions(List<SecurityAction> actions) {this.actions = actions;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof SecurityView)
             ? id == ((SecurityView) object).getId()
             : (object == this);
    }
}