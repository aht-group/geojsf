package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.ejb.meta.EjbGeoDataSourceFactory;
import org.geojsf.interfaces.facade.GeoJsfUtilsFacade;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
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
									CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
									RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfDataSourceBean.class);
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	private EjbGeoDataSourceFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> efDs;
	
	protected GeoJsfUtilsFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fGeo;
		
	private String[] langKeys;
	private Class<DS> cDs;
	
	public void initSuper(String[] langKeys, final Class<L> cL, final Class<D> cD, final Class<DS> cDs, final Class<LAYER> cLayer)
	{
		this.langKeys=langKeys;
		this.cDs=cDs;
		efLang = EjbLangFactory.createFactory(cL);
    	efDescription = EjbDescriptionFactory.createFactory(cD);
    	efDs = EjbGeoDataSourceFactory.factory(cL, cD, cDs);
    	availableLayers = fGeo.all(cLayer);
	}
	
	//Datasources
	protected List<DS> sources;
	public List<DS> getSources() {return sources;}
	
	protected void reloadSources()
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
//		service = efService.build(null, null);
		
//		service.setName(efLang.createEmpty(langKeys));
//		service.setDescription(efDescription.createEmpty(langKeys));
		
	}

	public void selectSource() throws UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(source));
//		service = fGeo.load(cService,service);
//		logger.info("selectService "+service);
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
		logger.info("selectLayer");
	}
	
	public void openLayers()
	{
		logger.info("openLayers");
	}
	
	public void addLayer()
	{
		logger.info("addLayer");
	}
	
	public void rmLayer()
	{
		logger.info("rmLayer");
	}
}