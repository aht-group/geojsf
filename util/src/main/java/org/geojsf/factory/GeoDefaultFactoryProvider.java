package org.geojsf.factory;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.model.pojo.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.core.DefaultGeoJsfView;
import org.geojsf.model.pojo.meta.DefaultGeoJsfScale;
import org.geojsf.model.pojo.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

public class GeoDefaultFactoryProvider
{	
	public static GeoCoreFactoryBuilder<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort> geoCore()
	{
		return new GeoCoreFactoryBuilder<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort>(DefaultGeoJsfLang.class,DefaultGeoJsfDescription.class,DefaultGeoJsfCategory.class,DefaultGeoJsfService.class,DefaultGeoJsfLayer.class,DefaultGeoJsfMap.class,DefaultGeoJsfScale.class,DefaultGeoJsfView.class,DefaultGeoJsfViewPort.class);
	}
}