package org.geojsf.interfaces.model;


import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoJsfView<L extends UtilsLang,
						D extends UtilsDescription,
						SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,
						LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,
						MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,
						VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
			extends EjbWithId,EjbRemoveable
{
	public static final String extractId = "geoJsfView";
	
	MAP getView();
	void setView(MAP view);
	
	LAYER getLayer();
	void setLayer(LAYER layer);
	
	int getOrderNo();
	void setOrderNo(int orderNo);
	
	Boolean isVisible();
	void setVisible(Boolean visible);
	
	Boolean isLegend();
	void setLegend(Boolean legend);
	
}