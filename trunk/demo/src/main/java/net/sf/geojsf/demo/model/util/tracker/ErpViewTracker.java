package net.sf.geojsf.demo.model.util.tracker;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.tracker.UtilsEventTracker;
import net.sf.geojsf.demo.model.user.GeoUser;
import net.sf.geojsf.demo.model.util.GeoDescription;
import net.sf.geojsf.demo.model.util.GeoLang;
import net.sf.geojsf.demo.model.util.security.SecurityAction;
import net.sf.geojsf.demo.model.util.security.SecurityCategory;
import net.sf.geojsf.demo.model.util.security.SecurityRole;
import net.sf.geojsf.demo.model.util.security.SecurityUsecase;
import net.sf.geojsf.demo.model.util.security.SecurityView;

@Entity
@Table(name = "TrackerView")
public class ErpViewTracker implements Serializable,EjbWithId,EjbPersistable,
		UtilsEventTracker<GeoLang,GeoDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,GeoUser,SecurityView>
{
	public static final long serialVersionUID=2;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
	
	@ManyToOne
	private GeoUser user;
	
	@NotNull
	private Date record;
	
	@NotNull @ManyToOne
	private SecurityView event;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<	
		
	@Override public long getId() {return id;}
	@Override public void setId(long id){this.id = id;}
	
	@Override public GeoUser getUser() {return user;}
	@Override public void setUser(GeoUser user) {this.user = user;}
	
	@Override public Date getRecord() {return record;}
	@Override public void setRecord(Date record) {this.record = record;}
	
	@Override public SecurityView getEvent() {return event;}
	@Override public void setEvent(SecurityView event) {this.event=event;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof ErpViewTracker)
             ? id == ((ErpViewTracker) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" record:"+record);
			sb.append(" user:"+user);
		return sb.toString();
	}
	
}