package net.sf.geojsf.demo.mbean.util;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.geojsf.controller.util.GeoJsfMapLayerFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoLayerFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoServiceFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoViewFactory;

import org.geojsf.controller.util.GeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfViewLayer;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @SessionScoped
public class GeoServiceBean implements Serializable
{
	private static final long serialVersionUID = -4869182190759908416L;
	final static Logger logger = LoggerFactory.getLogger(GeoServiceBean.class);
	private String text;
	
	private GeoJsfMapLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fJsf;
	private EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fService;
	private EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fLayer;
	private EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fView;
	
	private GeoJsfMap<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> geoJsfMap;

	private List<DefaultGeoJsfService> openLayerMulti;

	@PostConstruct
	public void init() throws UtilsIntegrityException
	{
		logger.info("@PostConstruct");
		
		
	}
	
	public void listen(AjaxBehaviorEvent evt)
	{
		logger.info("Received event: " +evt.toString());
	}

	public String getText() {return text;}
	public void setText(String text) {this.text = text;}

	public GeoJsfMap<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> getGeoJsfMap() {return geoJsfMap;}
	public List<DefaultGeoJsfService> getOpenLayerMulti() {return openLayerMulti;}
}