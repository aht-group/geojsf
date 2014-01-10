package org.geojsf.interfaces.model;


import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoJsfViewLayer<L extends UtilsLang,
						D extends UtilsDescription,
						SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
						LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
						VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,
						VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
			extends EjbWithId,EjbRemoveable
{
	public static final String extractId = "geoJsfView";
	
	VIEW getView();
	void setView(VIEW view);
	
	LAYER getLayer();
	void setLayer(LAYER layer);
	
	int getOrderNo();
	void setOrderNo(int orderNo);
	
	Boolean isVisible();
	void setVisible(Boolean visible);
	
	Boolean isLegend();
	void setLegend(Boolean legend);
	
}