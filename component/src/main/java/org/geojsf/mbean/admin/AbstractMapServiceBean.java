package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.EjbGeoMapFactory;
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.EjbGeoViewFactory;
import org.geojsf.interfaces.facade.GeoJsfUtilsFacade;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractMapServiceBean <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>> 
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMapServiceBean.class);
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	protected EjbGeoServiceFactory<L,D,SERVICE,LAYER,MAP,VIEW> efService;
	protected EjbGeoLayerFactory<L,D,SERVICE,LAYER,MAP,VIEW> efLayer;
	protected EjbGeoMapFactory<L,D,SERVICE,LAYER,MAP,VIEW> efMap;
	protected EjbGeoViewFactory<L,D,SERVICE,LAYER,MAP,VIEW> efView;
	
	protected GeoJsfUtilsFacade fGeo;
	
	protected MAP map;
	public MAP getMap() {return map;}
	
	protected List<SERVICE> services;
	public List<SERVICE> getServices() {return services;}
	
	protected List<LAYER> layers;
	public List<LAYER> getLayers() {return layers;}
	
	protected SERVICE service;
	public SERVICE getService() {return service;}
	public void setService(SERVICE service) {this.service = service;}
	
	protected LAYER layer;
	public LAYER getLayer() {return layer;}
	public void setLayer(LAYER layer) {this.layer = layer;}
	
	private String[] langKeys;
	private Class<SERVICE> cService;
	private Class<LAYER> cLayer;
	
	public void initSuper(String[] langKeys, final Class<L> cLang, final Class<D> clDescription, final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap, final Class<VIEW> cView)
	{
		this.langKeys=langKeys;
		this.cService=cService;
		this.cLayer=cLayer;
		efLang = EjbLangFactory.createFactory(cLang);
    	efDescription = EjbDescriptionFactory.createFactory(clDescription);
    	efService = EjbGeoServiceFactory.factory(cService);
    	efMap = EjbGeoMapFactory.factory(cLang, cMap);
    	efView = EjbGeoViewFactory.factory(cView);
	}
	
	protected void reloadServices()
	{
		services = fGeo.all(cService);
	}
	
	public void addService() throws UtilsIntegrityException, InstantiationException, IllegalAccessException
	{
		service = efService.build("ocde", "http");
		
		service.setName(efLang.createEmpty(langKeys));
		service.setDescription(efDescription.createEmpty(langKeys));
		
		layer=null;
	}

	public void selectService() throws UtilsNotFoundException
	{
		service = fGeo.load(cService,service);
		logger.info("selectService "+service);
		reloadLayer();
		layer=null;
	}
	
	public void saveService() throws UtilsContraintViolationException, UtilsLockingException
	{
		logger.info("saveService "+service);
		service = fGeo.save(service);
		reloadServices();
		reloadLayer();
	}
	
	
	// LAYER
	protected void reloadLayer()
	{
		service = fGeo.load(cService, service);
		layers = service.getLayer();
		logger.info("#Layer:"+layers.size());
	}
	
	public void addLayer() throws UtilsIntegrityException, UtilsNotFoundException, InstantiationException, IllegalAccessException
	{
		logger.info("addLayer ");
		layer = efLayer.build("", service,langKeys);
		
		layer.setDescription(efDescription.createEmpty(langKeys));
	}
	
	public void selectLayer() throws UtilsNotFoundException
	{
		logger.info("selectLayer "+layer);
		layer = fGeo.load(cLayer,layer);
		
		VIEW view = efView.build();
		view.setLayer(layer);
		
		map = efMap.build();
		map.getViews().add(view);
	}
	
	public void saveLayer() throws UtilsContraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info("saveLayer "+layer);
		layer.setService(fGeo.find(cService, layer.getService().getId()));
		layer = fGeo.save(layer);
		reloadLayer();
	}
}