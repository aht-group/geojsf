package org.geojsf.interfaces.model.core;


import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoJsfView<L extends UtilsLang, D extends UtilsDescription,
						G extends JeeslGraphic<L,D,G,GT,FS>,
						GT extends UtilsStatus<GT,L,D>,
						FS extends UtilsStatus<FS,L,D>,
						CATEGORY extends GeoJsfCategory<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						SERVICE extends GeoJsfService<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						LAYER extends GeoJsfLayer<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						MAP extends GeoJsfMap<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						SCALE extends GeoJsfScale<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						VIEW extends GeoJsfView<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						VP extends GeoJsfViewPort<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						DS extends GeoJsfDataSource<L,D,G,GT,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
						SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
						SLD extends GeoJsfSld<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
			extends EjbWithId,EjbSaveable,Comparable<VIEW>
{
	public static final String extractId = "geoJsfView";
	
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