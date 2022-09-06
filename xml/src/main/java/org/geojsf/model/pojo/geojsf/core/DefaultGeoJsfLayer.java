package org.geojsf.model.pojo.geojsf.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.model.pojo.geojsf.meta.DefaultGeoJsfDataSource;
import org.geojsf.model.pojo.geojsf.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.geojsf.sld.DefaultGeoJsfSld;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="Layer",category="core",subset="core,viewport,datasource")
public class DefaultGeoJsfLayer implements GeoJsfLayer<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSld>
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
	@Override public boolean isTemporalLayer() {return temporalLayer;}
	@Override public void setTemporalLayer(boolean temporalLayer) {this.temporalLayer = temporalLayer;}
	
	private Boolean sqlLayer;
	@Override public Boolean getSqlLayer(){return sqlLayer;}
	@Override public Boolean isSqlLayer() {return sqlLayer;}
	@Override public void setSqlLayer(Boolean sqlLayer) {this.sqlLayer = sqlLayer;}

	private DefaultGeoJsfSld sld;
	@Override public DefaultGeoJsfSld getSld() {return sld;}
	@Override public void setSld(DefaultGeoJsfSld sld) {this.sld = sld;}
	
	@ManyToMany
	private List<DefaultGeoJsfDataSource> sources;
	@Override public List<DefaultGeoJsfDataSource> getSources() {if(sources==null){sources = new ArrayList<DefaultGeoJsfDataSource>();};return sources;}
	@Override public void setSources(List<DefaultGeoJsfDataSource> sources) {this.sources = sources;}
	
	@Basic @Column(columnDefinition="text")
	private String remark;
	@Override public String getRemark() {return remark;}
	@Override public void setRemark(String remark) {this.remark = remark;}
	
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