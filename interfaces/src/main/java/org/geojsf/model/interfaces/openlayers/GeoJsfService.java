package org.geojsf.model.interfaces.openlayers;

import java.util.List;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface GeoJsfService<L extends UtilsLang,
								D extends UtilsDescription,
								SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
								LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
								VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,
								VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
			extends EjbWithId,EjbWithCode
{
	public static final String extractId = "geoJsfService";
	
	String getUrl();
	void setUrl(String url);
	
	List<LAYER> getLayer();
	void setLayer(List<LAYER> layer);
	
}