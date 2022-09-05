package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.ArrayList;
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

public class AbstractGeoJsonBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
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
									JL extends GeoJsfLocationLevel<JL,L,D,?>>
	extends AbstractGeoJsfBean<L,D,LOC,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE,JSON,JQ,JL>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsonBean.class);
	
	private final List<JSON> jsons; public List<JSON> getJsons() {return jsons;}
	private final List<JQ> qualities; public List<JQ> getQualities() {return qualities;}
	private final List<JL> levels; public List<JL> getLevels() {return levels;}
	
	protected JSON json; public JSON getJson() {return json;} public void setJson(JSON json) {this.json = json;}
		
	public AbstractGeoJsonBean(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore,
									GeoMetaFactoryBuilder<L,D,DS,VP,JSON,JQ,JL> fbMeta,
									GeoSldFactoryBuilder<L,D,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld)
	{
		super(fbCore,fbMeta,fbSld);
		
		jsons = new ArrayList<JSON>();
		qualities = new ArrayList<JQ>();
		levels = new ArrayList<JL>();
	}
	
	public void postConstructJson(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
								GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS> fGeo)
	{
		super.postConstructGeojsf(bTranslation,bMessage,fGeo);
		
		if(qualities==null) {logger.warn("qualities==null");}
		if(fGeo==null) {logger.warn("fGeo==null");}
		if(fbMeta==null) {logger.warn("fbMeta==null");}
		
		qualities.addAll(fGeo.allOrderedPositionVisible(fbMeta.getClassQuality()));
		levels.addAll(fGeo.allOrderedPositionVisible(fbMeta.getClassLevel()));
		reloadJsons();
	}
	
	public void cancelJson() {reset(true);}
	private void reset(boolean rJson)
	{
		if(rJson) {json=null;}
	}
	
	private void reloadJsons()
	{
		jsons.clear();
		jsons.addAll(fGeo.all(fbMeta.getClassJson()));
	}
	
	public void addJson()
	{
		json = fbMeta.ejbJson().build();
	}
	
	public void selectJson()
	{
		json = fGeo.find(fbMeta.getClassJson(),json);
	}
	
	public void saveJson() throws JeeslConstraintViolationException, JeeslLockingException
	{
		json.setQuality(fGeo.find(fbMeta.getClassQuality(),json.getQuality()));
		json.setLevel(fGeo.find(fbMeta.getClassLevel(),json.getLevel()));
		json = fGeo.save(json);
		reloadJsons();
	}
}