package org.geojsf.model.pojo.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.model.pojo.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.core.DefaultGeoJsfView;
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

@EjbErNode(name="Data Source",category="meta",subset="datasource")
public class DefaultGeoJsfDataSource implements Serializable,EjbRemoveable,EjbPersistable,
								GeoJsfDataSource<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphic,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicStyle,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldTemplate,DefaultGeoJsfSldType,DefaultGeoJsfSld,DefaultGeoJsfSldRule>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private Map<String, DefaultGeoJsfLang> name;
	@Override public Map<String, DefaultGeoJsfLang> getName() {return name;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {this.name = name;}
	
	private Map<String, DefaultGeoJsfDescription> description;
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return description;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {this.description = description;}

	@ManyToMany(fetch=FetchType.LAZY)
	private List<DefaultGeoJsfLayer> layers;
	@Override public List<DefaultGeoJsfLayer> getLayers() {if(layers==null){layers = new ArrayList<DefaultGeoJsfLayer>();};return layers;}
	@Override public void setLayers(List<DefaultGeoJsfLayer> layers) {this.layers = layers;}

	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}

	@Override public boolean equals(Object object){return (object instanceof DefaultGeoJsfDataSource) ? id == ((DefaultGeoJsfDataSource) object).getId() : (object == this);}
}