package net.sf.geojsf.demo.mbean.showcase;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.geojsf.controller.util.GeoJsfMap;

import net.sf.geojsf.controller.util.DummyViewFactory;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfViewLayer;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;

@Named @SessionScoped
public class ShowcaseMapBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private GeoJsfMap<DefaultGeoJsfLang,DefaultGeoJsfDescription,
				DefaultGeoJsfService,DefaultGeoJsfLayer,
				DefaultGeoJsfView,DefaultGeoJsfViewLayer>
			geoJsfMap;

	@PostConstruct
	public void init() throws FileNotFoundException
	{		
		geoJsfMap = GeoJsfMap.factory(
			DefaultGeoJsfService.class, DummyViewFactory.build());
		geoJsfMap.debug();
	}
	
	public GeoJsfMap<DefaultGeoJsfLang,DefaultGeoJsfDescription,
				DefaultGeoJsfService,DefaultGeoJsfLayer,
				DefaultGeoJsfView,DefaultGeoJsfViewLayer>
	getGeoJsfMap() {return geoJsfMap;}
}