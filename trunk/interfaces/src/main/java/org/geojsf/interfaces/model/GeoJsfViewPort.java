package org.geojsf.interfaces.model;


import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoJsfViewPort<L extends UtilsLang,
						D extends UtilsDescription,
						SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW,VP>,
						LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW,VP>,
						MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW,VP>,
						VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW,VP>,
						VP extends GeoJsfViewPort<L,D,SERVICE,LAYER,MAP,VIEW,VP>>
			extends EjbWithId,EjbRemoveable
{
	public static final String extractId = "geoJsfViewPort";
	
	double getLat();
	void setLat(double lat);
	
	double getLon();
	void setLon(double lon);
	
	double getScale();
	void setScale(double scale);
}