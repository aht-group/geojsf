package org.geojsf.interfaces.model;


import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoJsfView<L extends UtilsLang,
						D extends UtilsDescription,
						CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
			extends EjbWithId,EjbSaveable,Comparable<VIEW>
{
	public static final String extractId = "geoJsfView";
	
	MAP getMap();
	void setMap(MAP view);
	
	LAYER getLayer();
	void setLayer(LAYER layer);
	
	int getOrderNo();
	void setOrderNo(int orderNo);
	
	boolean getVisible();
	boolean isVisible();
	void setVisible(boolean visible);
	
	Boolean isLegend();
	void setLegend(Boolean legend);
	
}