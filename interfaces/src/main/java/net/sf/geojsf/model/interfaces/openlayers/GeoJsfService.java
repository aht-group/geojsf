package net.sf.geojsf.model.interfaces.openlayers;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface GeoJsfService<L extends UtilsLang,
								D extends UtilsDescription,
								LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
								SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
			extends EjbWithId,EjbWithCode
{
	public static final String extractId = "geoJsfService";
	
//	List<LAYER> getLayer();
//	void setLayer(List<LAYER> layer);
	
}