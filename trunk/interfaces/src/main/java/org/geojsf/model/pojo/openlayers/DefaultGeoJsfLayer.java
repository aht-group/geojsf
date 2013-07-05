package org.geojsf.model.pojo.openlayers;

import java.io.Serializable;
import java.util.Map;

import org.geojsf.interfaces.model.openlayers.GeoJsfLayer;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;

public class DefaultGeoJsfLayer implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfLayer<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer>
{
	public static enum Code {welcome}
	
	public static final long serialVersionUID=1;
	
	private long id;
	
	private DefaultGeoJsfService service;

	private String code;
	
	private Map<String, DefaultGeoJsfLang> name;
	
	private Map<String, DefaultGeoJsfDescription> description;
	
	//******************************************************************************
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	@Override public Map<String, DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}
	
	@Override public DefaultGeoJsfService getService() {return service;}
	@Override public void setService(DefaultGeoJsfService service) {this.service = service;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfLayer)
             ? id == ((DefaultGeoJsfLayer) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}