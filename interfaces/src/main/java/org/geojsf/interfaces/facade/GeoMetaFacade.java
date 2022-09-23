package org.geojsf.interfaces.facade;

import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

public interface GeoMetaFacade <L extends JeeslLang, D extends JeeslDescription,
									MAP extends GeoJsfMap<L,D,?,?,VP>,
									DS extends GeoJsfDataSource<L,D,?>,
									VP extends GeoJsfViewPort,
									SCALE extends GeoJsfScale<L,D> >
					extends JeeslFacade
{
	DS load(DS ds);
	
//	List<DS> fDataSources(Class<MAP> cMap, Class<DS> cDs, MAP map);
}