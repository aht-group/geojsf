package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.json.GeoJsfJsonData;
import org.geojsf.interfaces.model.json.GeoJsfJsonQuality;
import org.geojsf.interfaces.model.json.GeoJsfLocationLevel;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicShape;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractGeoJsfDataSourceBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
									F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
									CATEGORY extends GeoJsfCategory<L,D,LAYER>,
									SERVICE extends GeoJsfService<L,D,LAYER>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
									MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
									SCALE extends GeoJsfScale<L,D>, 
									VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
									VP extends GeoJsfViewPort,
									DS extends GeoJsfDataSource<L,D,LAYER>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
									SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>,
									JSON extends GeoJsfJsonData<L,D,JQ,JL>,
									JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
									JL extends GeoJsfLocationLevel<L,D,JL,?>>
	extends AbstractGeoJsfBean<L,D,LOC,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE,JSON,JQ,JL>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfDataSourceBean.class);
	
	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<DS> sources; public List<DS> getSources() {return sources;}
	
	public AbstractGeoJsfDataSourceBean(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore,
										GeoMetaFactoryBuilder<L,D,DS,VP,JSON,JQ,JL> fbMeta,
										GeoSldFactoryBuilder<L,D,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld)
	{
		super(fbCore,fbMeta,fbSld);
	}
	
	public void postConstructDataSource(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
							GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS> fGeo)
	{
		super.postConstructGeojsf(bTranslation,bMessage,fGeo);

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
		source = efDs.build(langKeys);		
	}

	public void selectSource() throws JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(source));
		reloadSource();
	}
	
	private void reloadSource()
	{
		source = fGeo.load(fbMeta.getClassDs(), source);
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