package org.geojsf.model.pojo.openlayers;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.ManyToOne;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldStyle;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

@EjbErNode(name="Layer",category="geojsf")
public class DefaultGeoJsfLayer implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfLayer<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfSldType,DefaultGeoJsfSldStyle,DefaultGeoJsfSldTemplate>
{
	public static enum Code {welcome}
	
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@ManyToOne
	private DefaultGeoJsfService service;
	@Override public DefaultGeoJsfService getService() {return service;}
	@Override public void setService(DefaultGeoJsfService service) {this.service = service;}
	
	@ManyToOne
	private DefaultGeoJsfCategory category;
	@Override public DefaultGeoJsfCategory getCategory(){return category;}
	@Override public void setCategory(DefaultGeoJsfCategory category){this.category = category;}

	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	private Map<String, DefaultGeoJsfLang> name;
	@Override public Map<String, DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
	private Map<String, DefaultGeoJsfDescription> description;
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}

	private DefaultGeoJsfViewPort viewPort;
	@Override public DefaultGeoJsfViewPort getViewPort(){return viewPort;}
	@Override public void setViewPort(DefaultGeoJsfViewPort viewPort){this.viewPort = viewPort;}
	
	private boolean temporalLayer;
	@Override public boolean getTemporalLayer(){return temporalLayer;}
	@Override public boolean isTemporalLayer() {return temporalLayer;}
	@Override public void setTemporalLayer(boolean temporalLayer) {this.temporalLayer = temporalLayer;}
	
	private Boolean sqlLayer;
	@Override public Boolean getSqlLayer(){return sqlLayer;}
	@Override public Boolean isSqlLayer() {return sqlLayer;}
	@Override public void setSqlLayer(Boolean sqlLayer) {this.sqlLayer = sqlLayer;}

	private DefaultGeoJsfSldTemplate sldTemplate;
	public DefaultGeoJsfSldTemplate getSldTemplate() {return sldTemplate;}
	public void setSldTemplate(DefaultGeoJsfSldTemplate sldTemplate) {this.sldTemplate = sldTemplate;}
	
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