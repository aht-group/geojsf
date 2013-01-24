package net.sf.geojsf.demo.mbean.showcase;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.geojsf.controller.util.GeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfViewLayer;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

import net.sf.geojsf.controller.util.DummyViewFactory;

//ahtutils.highlight:showcase
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
//ahtutils.highlight:showcase