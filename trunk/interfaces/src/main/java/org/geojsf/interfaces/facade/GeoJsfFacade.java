package org.geojsf.interfaces.facade;

import net.sf.ahtutils.interfaces.facade.UtilsIdFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

public interface GeoJsfFacade <L extends UtilsLang,
								D extends UtilsDescription,
								CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
								SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
								MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
								VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
								VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,
								SLD extends GeoJsfSld<L,D,SLDTYPE,SLD,RULE>,
								RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLD,RULE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>

					extends UtilsIdFacade
{		
	SERVICE load(Class<SERVICE> cService, SERVICE service);
	CATEGORY load(Class<CATEGORY> cCategory, CATEGORY category);
	MAP load(Class<MAP> cMap, MAP map);
	LAYER load(Class<LAYER> cLayer, LAYER layer);
	SLD load(Class<SLD> cSld,SLD sld);
	
	void rm(Class<LAYER> cLayer, LAYER layer);	
	void rm(Class<VIEW> cView, VIEW view);
	
	
}