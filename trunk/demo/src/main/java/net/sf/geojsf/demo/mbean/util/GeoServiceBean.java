package net.sf.geojsf.demo.mbean.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.geojsf.controller.util.GeoJsfMap;
import net.sf.geojsf.controller.util.GeoJsfMapLayerFactory;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoLayerFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoServiceFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoViewFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @SessionScoped
public class GeoServiceBean implements Serializable
{
	private static final long serialVersionUID = -4869182190759908416L;
	final static Logger logger = LoggerFactory.getLogger(GeoServiceBean.class);
	private String text;
	
	private GeoJsfMapLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fJsf;
	private EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fService;
	private EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fLayer;
	private EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fView;
	
	private GeoJsfMap<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> geoJsfMap;

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

	public GeoJsfMap<DefaultGeoJsfLang, DefaultGeoJsfDescription, DefaultGeoJsfLayer, DefaultGeoJsfView, DefaultGeoJsfService> getGeoJsfMap() {return geoJsfMap;}
	public List<DefaultGeoJsfService> getOpenLayerMulti() {return openLayerMulti;}
}
