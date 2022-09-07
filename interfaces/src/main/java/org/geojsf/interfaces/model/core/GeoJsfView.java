package org.geojsf.interfaces.model.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface GeoJsfView<LAYER extends GeoJsfLayer<?,?,?,?,?,?,?>,
							MAP extends GeoJsfMap<?,?,?,VIEW,?>,	
							VIEW extends GeoJsfView<LAYER,MAP,VIEW>>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbSaveable,Comparable<VIEW>
{
	public static final String extractId = "geoJsfView";
	public enum Attributes{map,layer}
	
	MAP getMap();
	void setMap(MAP view);
	
	LAYER getLayer();
	void setLayer(LAYER layer);
	
	int getOrderNo();
	void setOrderNo(int orderNo);
	
	Integer getLegendNo();
	void setLegendNo(Integer orderNo);
	
	boolean getVisible();
	boolean isVisible();
	void setVisible(boolean visible);
	
	Boolean getLegend();
	void setLegend(Boolean legend);
	
	Boolean getLegendSymbol();
	void setLegendSymbol(Boolean legendSymbol);
}