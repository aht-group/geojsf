package org.geojsf.model.pojo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.model.pojo.meta.DefaultGeoJsfDataSource;
import org.geojsf.model.pojo.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSld;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldRule;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphic;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicStyle;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicType;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Layer",category="core",subset="core,viewport,datasource")
public class DefaultGeoJsfLayer implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfLayer<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphic,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicStyle,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldTemplate,DefaultGeoJsfSldType,DefaultGeoJsfSld,DefaultGeoJsfSldRule>
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
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}

	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	@Override public String resolveParentAttribute() {return "category";}
	
	private Map<String, DefaultGeoJsfLang> name;
	@Override public Map<String, DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
	private Map<String, DefaultGeoJsfDescription> description;
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}

	@OneToOne
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
	@Override public DefaultGeoJsfSldTemplate getSldTemplate() {return sldTemplate;}
	@Override public void setSldTemplate(DefaultGeoJsfSldTemplate sldTemplate) {this.sldTemplate = sldTemplate;}
	
	@ManyToMany
	private List<DefaultGeoJsfDataSource> sources;
	@Override public List<DefaultGeoJsfDataSource> getSources() {if(sources==null){sources = new ArrayList<DefaultGeoJsfDataSource>();};return sources;}
	@Override public void setSources(List<DefaultGeoJsfDataSource> sources) {this.sources = sources;}
	
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