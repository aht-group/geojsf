package net.sf.geojsf.model.pojo.openlayers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;

public class DefaultGeoJsfView implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfView<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer>
{
	public static enum Code {welcome}
	
	public static final long serialVersionUID=1;
	
	private long id;
	
	private String code;
	
	private Map<String, DefaultGeoJsfLang> name;
	
	private Map<String, DefaultGeoJsfDescription> description;
	
	private List<DefaultGeoJsfViewLayer> layer;
	
	//******************************************************************************
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	@Override public Map<String, DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}
	
	@Override public List<DefaultGeoJsfViewLayer> getLayer() {if(layer==null){layer=new ArrayList<DefaultGeoJsfViewLayer>();}return layer;}
	@Override public void setLayer(List<DefaultGeoJsfViewLayer> layer) {this.layer=layer;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfView)
             ? id == ((DefaultGeoJsfView) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}