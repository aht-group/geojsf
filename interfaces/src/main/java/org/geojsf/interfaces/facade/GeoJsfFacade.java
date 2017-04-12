package org.geojsf.interfaces.facade;

import java.util.List;

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
import org.geojsf.interfaces.model.with.EjbWithSldRules;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface GeoJsfFacade <L extends UtilsLang,
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
								RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
								>
					extends UtilsFacade
{		
	SERVICE load(Class<SERVICE> cService, SERVICE service);
	CATEGORY load(Class<CATEGORY> cCategory, CATEGORY category);
	MAP load(Class<MAP> cMap, MAP map);
	LAYER load(Class<LAYER> cLayer, LAYER layer);
	SLD load(SLD sld);
	DS load(Class<DS> cDs, DS ds);
	RULE load(Class<RULE> cRule, RULE rule);
	
	void rm(Class<LAYER> cLayer, LAYER layer);	
	void rm(Class<VIEW> cView, VIEW view);
	
	List<DS> fDataSources(Class<MAP> cMap, Class<DS> cDs, MAP map);
	List<SLD> fLibrarySlds();
	
	<W extends EjbWithSldRules<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>> RULE save(Class<W> cW, W entity, RULE rule) throws UtilsLockingException, UtilsConstraintViolationException;
	<W extends EjbWithSldRules<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>> void rm(Class<W> cW, W entity, RULE rule) throws UtilsLockingException, UtilsConstraintViolationException;
}