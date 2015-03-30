package org.geojsf.interfaces.model;


import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfLayer<L extends UtilsLang,
							D extends UtilsDescription,
							CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
							SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
							MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
							VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
							VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
			extends EjbSaveable,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	SERVICE getService();
	void setService(SERVICE service);
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	VP getViewPort();
	void setViewPort(VP viewPort);
	
	boolean getTemporalLayer();
	boolean isTemporalLayer();
	void setTemporalLayer(boolean temporalLayer);
	
	Boolean isSqlLayer();
	Boolean getSqlLayer();
	void setSqlLayer(Boolean sqlLayer);
	
	SLDTEMPLATE getSldTemplate();
	void setSldTemplate(SLDTEMPLATE sldTemplate);
}