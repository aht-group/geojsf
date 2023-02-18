package org.geojsf.test;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.model.pojo.geojsf.core.DfGeoJsfCategory;
import org.geojsf.model.pojo.geojsf.core.DfGeoJsfLayer;
import org.geojsf.model.pojo.geojsf.core.DfGeoJsfLayerType;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfView;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;

public class GeoDefaultFactoryProvider
{	
	public static GeoCoreFactoryBuilder<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfGeoJsfCategory,DefaultGeoJsfService,DfGeoJsfLayer,DfGeoJsfLayerType,DefaultGeoJsfMap,DefaultGeoJsfView> geoCore()
	{
		return new GeoCoreFactoryBuilder<>(DefaultGeoJsfLang.class,DefaultGeoJsfDescription.class,DfGeoJsfCategory.class,DefaultGeoJsfService.class,DfGeoJsfLayer.class,DfGeoJsfLayerType.class,DefaultGeoJsfMap.class,DefaultGeoJsfView.class);
	}
}