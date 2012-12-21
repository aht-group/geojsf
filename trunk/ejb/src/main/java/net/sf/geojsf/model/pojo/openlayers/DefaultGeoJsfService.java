package net.sf.geojsf.model.pojo.openlayers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.model.interfaces.openlayers.GeoJsfService;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;

public class DefaultGeoJsfService implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfService<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer>
{
	public static enum Code {welcome}
	
	public static final long serialVersionUID=1;
	
	private long id;
	
	private String code;
	
	private String url;
	
	private List<DefaultGeoJsfLayer> layer;
	
	//******************************************************************************
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	@Override public String getUrl() {return url;}
	@Override public void setUrl(String url) {this.url=url;}
	
	@Override public List<DefaultGeoJsfLayer> getLayer() {if(layer==null){layer = new ArrayList<DefaultGeoJsfLayer>();} return layer;}
	@Override public void setLayer(List<DefaultGeoJsfLayer> layer) {this.layer=layer;}
	
//	@Override public List<GeoLayer> getLayer() {if(layer==null){layer=new ArrayList<GeoLayer>();}return layer;}
//	@Override public void setLayer(List<GeoLayer> layer) {this.layer=layer;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfService)
             ? id == ((DefaultGeoJsfService) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}