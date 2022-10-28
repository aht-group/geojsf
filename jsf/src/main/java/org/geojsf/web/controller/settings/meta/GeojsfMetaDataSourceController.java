package org.geojsf.web.controller.settings.meta;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.ejb.meta.EjbGeoDataSourceFactory;
import org.geojsf.interfaces.facade.GeoMetaFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.web.controller.AbstractJeeslWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class GeojsfMetaDataSourceController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									CATEGORY extends GeoJsfCategory<L,D,LAYER>,
									
									LAYER extends GeoJsfLayer<L,D,CATEGORY,?,?,DS,?>,
									
									DS extends GeoJsfDataSource<L,D,LAYER>>
		extends AbstractJeeslWebController<L,D,LOC>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeojsfMetaDataSourceController.class);
	
	private GeoMetaFacade<L,D,?,DS,?,?> fGeo;
	
	private final GeoCoreFactoryBuilder<L,D,CATEGORY,?,LAYER,?,?> fbCore;
	private final GeoMetaFactoryBuilder<L,D,DS,?,?> fbMeta;
	
	protected final EjbGeoDataSourceFactory<L,D,DS> efDs;
	
	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<DS> sources; public List<DS> getSources() {return sources;}
	
	public GeojsfMetaDataSourceController(GeoCoreFactoryBuilder<L,D,CATEGORY,?,LAYER,?,?> fbCore,
										GeoMetaFactoryBuilder<L,D,DS,?,?> fbMeta)
	{
		super(fbMeta.getClassL(),fbMeta.getClassD());
		this.fbCore=fbCore;
		this.fbMeta=fbMeta;
		
		efDs = fbMeta.ejbDs();
	}
	
	public void postConstructDataSource(JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage,
			GeoMetaFacade<L,D,?,DS,?,?> fGeo)
	{
		super.postConstructWebController(lp,bMessage);
		this.fGeo=fGeo;

	    availableLayers = fGeo.all(fbCore.getClassLayer());
	    categories = fGeo.all(fbCore.getClassCategory());
		reloadSources();
	}
	

	
	private void reloadSources()
	{
		sources = fGeo.all(fbMeta.getClassDs());
	}
	
	//DataSource
	protected DS source;
	public DS getSource() {return source;}
	public void setSource(DS source) {this.source = source;}
	
	public void addSource() throws JeeslConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(fbMeta.getClassDs()));
		source = efDs.build();
		source.setName(efLang.buildEmpty(lp.getLocales()));
		source.setDescription(efDescription.buildEmpty(lp.getLocales()));
	}

	public void selectSource() throws JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(source));
		reloadSource();
	}
	
	private void reloadSource()
	{
		source = fGeo.load(source);
		layers = source.getLayers();
	}
	
	public void rmSource() throws JeeslConstraintViolationException
	{
		logger.info("rm "+source);
		fGeo.rm(source);
		source=null;
		reloadSources();
	}
	
	public void saveSource() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("saveService "+source);
		source = fGeo.save(source);
		reloadSources();
		reloadSource();
	}
	
	public void cancelSource() throws JeeslConstraintViolationException, JeeslLockingException
	{
		source=null;
	}
	
	//Layers
	private List<LAYER> layers;
	public List<LAYER> getLayers() {return layers;}
	
	private List<LAYER> availableLayers;
	public List<LAYER> getAvailableLayers() {return availableLayers;}
	
	//Layer
	private LAYER layer;
	public LAYER getLayer() {return layer;}
	public void setLayer(LAYER layer) {this.layer = layer;}
	
	private LAYER availableLayer;
	public LAYER getAvailableLayer() {return availableLayer;}
	public void setAvailableLayer(LAYER availableLayer) {this.availableLayer = availableLayer;}

	public void selectLayer()
	{
		logger.info("selectLayer "+layer.toString());
	}
	
	public void showAvailable()
	{
		logger.info("showAvailable");
	}
	
	public void addLayer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("addLayer");
		if(!source.getLayers().contains(availableLayer))
		{
			source.getLayers().add(availableLayer);
			source = fGeo.save(source);
			reloadSource();
		}
		layer=null;
	}
	
	public void rmLayer() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("rmLayer");
		if(source.getLayers().contains(layer))
		{
			source.getLayers().remove(layer);
			source = fGeo.save(source);
			reloadSource();
		}
		layer=null;
	}
}