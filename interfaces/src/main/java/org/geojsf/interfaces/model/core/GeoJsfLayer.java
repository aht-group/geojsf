package org.geojsf.interfaces.model.core;

import java.io.Serializable;
import java.util.List;

import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisibleParent;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslAttributes;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslDescription;

@DownloadJeeslDescription
@DownloadJeeslAttributes
public interface GeoJsfLayer<L extends JeeslLang, D extends JeeslDescription,
							CATEGORY extends GeoJsfCategory<L,D,?>,
							SERVICE extends GeoJsfService<L,D,?>,
//							LT extends GeoJsfLayerType<L,D,LT,?>,
							VP extends GeoJsfViewPort,
							DS extends GeoJsfDataSource<L,D,?>,
							SLD extends GeoJsfSld<L,D,?,?,?>>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,EjbWithCode,EjbWithPositionVisibleParent,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{category,name}
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	SERVICE getService();
	void setService(SERVICE service);

	VP getViewPort();
	void setViewPort(VP viewPort);
	
	boolean isTemporalLayer();
	void setTemporalLayer(boolean temporalLayer);
	
	Boolean isSqlLayer();
	Boolean getSqlLayer();
	void setSqlLayer(Boolean sqlLayer);
	
	SLD getSld();
	void setSld(SLD sld);
	
	List<DS> getSources();
	void setSources(List<DS> sources);
	
	String getRemark();
	void setRemark(String remark);
}