package org.geojsf.interfaces.model;


import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoJsfViewPort<L extends UtilsLang,
						D extends UtilsDescription,
						CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
						VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
			extends EjbWithId,EjbRemoveable,EjbSaveable
{	
	double getLat();
	void setLat(double lat);
	
	double getLon();
	void setLon(double lon);
	
	double getScale();
	void setScale(double scale);
	
	// **************************
	
	double getMarginLeft();
	void setMarginLeft(double marginLeft);
	
	double getMarginRight();
	void setMarginRight(double marginRight);
	
	double getMarginTop();
	void setMarginTop(double marginTop);
	
	double getMarginBottom();
	void setMarginBottom(double marginBottom);
}