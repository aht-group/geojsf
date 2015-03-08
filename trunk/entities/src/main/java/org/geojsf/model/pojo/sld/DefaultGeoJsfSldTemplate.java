package org.geojsf.model.pojo.sld;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

@EjbErNode(name="ViewPort",category="geojsf")
public class DefaultGeoJsfSldTemplate implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfSldTemplate<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfSldType,DefaultGeoJsfSldTemplate>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private DefaultGeoJsfSldType type;
	public DefaultGeoJsfSldType getType() {return type;}
	public void setType(DefaultGeoJsfSldType type) {this.type = type;}
	

	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfSldTemplate) ? id == ((DefaultGeoJsfSldTemplate) object).getId() : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}

	
}