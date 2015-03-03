package org.geojsf.interfaces.facade;

import net.sf.ahtutils.interfaces.facade.UtilsIdFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;

public interface GeoJsfFacade <L extends UtilsLang,
								D extends UtilsDescription,
								CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
								SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
								MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
								VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
								VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>

					extends UtilsIdFacade
{		
	SERVICE load(Class<SERVICE> cService, SERVICE service);
	CATEGORY load(Class<CATEGORY> cCategory, CATEGORY category);
	MAP load(Class<MAP> cMap, MAP map);
	LAYER load(Class<LAYER> cLayer, LAYER layer);
	
	void rm(Class<LAYER> cLayer, LAYER layer);	
	void rm(Class<VIEW> cView, VIEW view);
}