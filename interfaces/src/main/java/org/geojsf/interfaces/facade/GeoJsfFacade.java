package org.geojsf.interfaces.facade;

import java.util.List;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

public interface GeoJsfFacade <L extends JeeslLang, D extends JeeslDescription,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,?>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort,
								DS extends GeoJsfDataSource<L,D,LAYER>
								>
					extends JeeslFacade
{		
	SERVICE load(Class<SERVICE> cService, SERVICE service);
	CATEGORY load(Class<CATEGORY> cCategory, CATEGORY category);
	MAP load(Class<MAP> cMap, MAP map);
	LAYER load(Class<LAYER> cLayer, LAYER layer);
	
	void rm(Class<LAYER> cLayer, LAYER layer);	
	void rm(Class<VIEW> cView, VIEW view);
	
	List<DS> fDataSources(Class<MAP> cMap, Class<DS> cDs, MAP map);
	List<VIEW> fGeoViews(LAYER layer);
}