package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.ejb.meta.EjbGeoDataSourceFactory;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractGeoJsfDataSourceBean <L extends UtilsLang,
									D extends UtilsDescription,
									G extends JeeslGraphic<L,D,G,GT,GS>,
									GT extends UtilsStatus<GT,L,D>,
									GS extends UtilsStatus<GS,L,D>,
									CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									SCALE extends GeoJsfScale<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, 
									VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
									RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfDataSourceBean.class);
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	private EjbGeoDataSourceFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efDs;
	
	protected GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo;
		
	private String[] langKeys;
	private Class<DS> cDs;
	
	public void initSuper(String[] langKeys, GeoJsfFacade<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fGeo, final Class<L> cL, final Class<D> cD, final Class<DS> cDs, final Class<LAYER> cLayer, final Class<CATEGORY> cCategory)
	{
		this.langKeys=langKeys;
		this.fGeo=fGeo;
		this.cDs=cDs;
		efLang = EjbLangFactory.createFactory(cL);
    	efDescription = EjbDescriptionFactory.createFactory(cD);
    	efDs = EjbGeoDataSourceFactory.factory(cL, cD, cDs);
    	availableLayers = fGeo.all(cLayer);
    	categories = fGeo.all(cCategory);
		reloadSources();
	}
	
	//Categories
	private List<CATEGORY> categories;
	public List<CATEGORY> getCategories() {return categories;}

	//Datasources
	private List<DS> sources;
	public List<DS> getSources() {return sources;}
	
	private void reloadSources()
	{
		sources = fGeo.all(cDs);
	}
	
	//DataSource
	protected DS source;
	public DS getSource() {return source;}
	public void setSource(DS source) {this.source = source;}
	
	public void addSource() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cDs));
		source = efDs.build(langKeys);		
	}

	public void selectSource() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(source));
//		service = fGeo.load(cService,service);
//		logger.info("selectService "+service);
		reloadSource();
	}
	
	private void reloadSource()
	{
		source = fGeo.load(cDs, source);
		layers = source.getLayers();
	}
	
	public void rmSource() throws UtilsConstraintViolationException
	{
		logger.info("rm "+source);
		fGeo.rm(source);
		source=null;
		reloadSources();
	}
	
	public void saveSource() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("saveService "+source);
		source = fGeo.save(source);
		reloadSources();
		reloadSource();
	}
	
	public void cancelSource() throws UtilsConstraintViolationException, UtilsLockingException
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
	
	public void addLayer() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("addLayer");
		if(!source.getLayers().contains(availableLayer))
		{
			source.getLayers().add(availableLayer);
			source = fGeo.update(source);
			reloadSource();
		}
		layer=null;
	}
	
	public void rmLayer() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info("rmLayer");
		if(source.getLayers().contains(layer))
		{
			source.getLayers().remove(layer);
			source = fGeo.update(source);
			reloadSource();
		}
		layer=null;
	}
}