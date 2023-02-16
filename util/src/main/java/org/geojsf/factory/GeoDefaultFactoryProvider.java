package org.geojsf.factory;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfLayerType;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfView;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;

public class GeoDefaultFactoryProvider
{	
	public static GeoCoreFactoryBuilder<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfLayerType,DefaultGeoJsfMap,DefaultGeoJsfView> geoCore()
	{
		return new GeoCoreFactoryBuilder<>(DefaultGeoJsfLang.class,DefaultGeoJsfDescription.class,DefaultGeoJsfCategory.class,DefaultGeoJsfService.class,DefaultGeoJsfLayer.class,DefaultGeoJsfLayerType.class,DefaultGeoJsfMap.class,DefaultGeoJsfView.class);
	}
}