package org.geojsf.interfaces.model.meta;


import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface GeoJsfViewPort extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbSaveable
{	
	double getLat();
	void setLat(double lat);
	
	double getLon();
	void setLon(double lon);
	
	int getScale();
	void setScale(int scale);
	
	double getZoom();
	void setZoom(double zoom);
	
	double getMarginLeft();
	void setMarginLeft(double marginLeft);
	
	double getMarginRight();
	void setMarginRight(double marginRight);
	
	double getMarginTop();
	void setMarginTop(double marginTop);
	
	double getMarginBottom();
	void setMarginBottom(double marginBottom);
}