package org.geojsf.web.controller.settings.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.builder.GeoJsonFactoryBuilder;
import org.geojsf.interfaces.facade.GeoMetaFacade;
import org.geojsf.interfaces.model.json.GeoJsfJsonData;
import org.geojsf.interfaces.model.json.GeoJsfJsonQuality;
import org.geojsf.interfaces.model.json.GeoJsfLocationLevel;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.controller.web.AbstractJeeslWebController;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeojsfGeoJsonController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									JSON extends GeoJsfJsonData<L,D,JQ,JL>,
									JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
									JL extends GeoJsfLocationLevel<L,D,JL,?>>
		extends AbstractJeeslWebController<L,D,LOC>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeojsfGeoJsonController.class);
	
	private GeoMetaFacade<L,D,?,?,?,?> fGeo;
	
	private final GeoJsonFactoryBuilder<L,D,JSON,JQ,JL> fbMeta;
	
	private final List<JSON> jsons; public List<JSON> getJsons() {return jsons;}
	private final List<JQ> qualities; public List<JQ> getQualities() {return qualities;}
	private final List<JL> levels; public List<JL> getLevels() {return levels;}
	
	protected JSON json; public JSON getJson() {return json;} public void setJson(JSON json) {this.json = json;}
		
	public GeojsfGeoJsonController(GeoJsonFactoryBuilder<L,D,JSON,JQ,JL> fbMeta)
	{
		super(fbMeta.getClassL(),fbMeta.getClassD());
		this.fbMeta=fbMeta;
		
		jsons = new ArrayList<JSON>();
		qualities = new ArrayList<JQ>();
		levels = new ArrayList<JL>();
	}
	
	public void postConstructJson(JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage,
									GeoMetaFacade<L,D,?,?,?,?> fGeo)
	{
		super.postConstructWebController(lp,bMessage);
		this.fGeo=fGeo;

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