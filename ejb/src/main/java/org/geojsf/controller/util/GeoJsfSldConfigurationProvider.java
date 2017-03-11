package org.geojsf.controller.util;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class GeoJsfSldConfigurationProvider<L extends UtilsLang,D extends UtilsDescription,
										G extends JeeslGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>,
										CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
										SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
										LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
										MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
										VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
										VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
										DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
										TEMPLATE extends GeoJsfSldTemplate<L,D,TEMPLATE,TYPE>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										SLD extends GeoJsfSld<L,D,G,GT,GS,TEMPLATE,TYPE,SLD,RULE>,
										RULE extends GeoJsfSldRule<L,D,G,GT,GS,TEMPLATE,TYPE,SLD,RULE>>
						implements SldConfigurationProvider<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>
{
	private String localeCode;
	@Override public String getLocaleCode() {return localeCode;}
	public void setLocaleCode(String localeCode) {this.localeCode = localeCode;}
	
	private UtilsFacade fJeesl;
	@Override public UtilsFacade getJeeslFacade() {return fJeesl;}
	public void setJeeslFacade(UtilsFacade fJeesl) {this.fJeesl = fJeesl;}
}