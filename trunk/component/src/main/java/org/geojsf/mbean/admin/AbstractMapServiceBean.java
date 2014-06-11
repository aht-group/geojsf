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
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
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
	protected EjbGeoServiceFactory<L,D,SERVICE,LAYER,MAP,VIEW> fService;
	protected EjbGeoLayerFactory<L,D,SERVICE,LAYER,MAP,VIEW> fLayer;
	
	protected GeoJsfUtilsFacade fGeo;
	
	protected MAP map;
	public MAP getMap() {return map;}
	
	protected List<SERVICE> services;
	public List<SERVICE> getServices() {return services;}
	
	protected SERVICE service;
	public SERVICE getService() {return service;}
	public void setService(SERVICE service) {this.service = service;}
	
	protected LAYER layer;
	public LAYER getLayer() {return layer;}
	public void setLayer(LAYER layer) {this.layer = layer;}
	
	private String[] langKeys;
	private Class<SERVICE> clService;
	
	public void initSuper(String[] langKeys, final Class<L> clLang, final Class<D> clDescription, final Class<SERVICE> clService)
	{
		this.langKeys=langKeys;
		this.clService=clService;
		efLang = EjbLangFactory.createFactory(clLang);
    	efDescription = EjbDescriptionFactory.createFactory(clDescription);
    	fService = EjbGeoServiceFactory.factory(clService);
	}
	
	protected void reloadServices()
	{
		services = fGeo.all(clService);
	}
	
	public void addService() throws UtilsIntegrityException, InstantiationException, IllegalAccessException
	{
		service = fService.build("ocde", "http");
		
		service.setName(efLang.createEmpty(langKeys));
		service.setDescription(efDescription.createEmpty(langKeys));
		
		layer=null;
	}

	public void selectService() throws UtilsNotFoundException
	{
		service = fGeo.load(clService,service);
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
	
	protected void reloadLayer(){}
}