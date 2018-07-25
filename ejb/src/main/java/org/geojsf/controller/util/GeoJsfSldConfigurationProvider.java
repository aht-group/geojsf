package org.geojsf.controller.util;

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
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class GeoJsfSldConfigurationProvider
						implements SldConfigurationProvider
{
	private String localeCode;
	@Override public String getLocaleCode() {return localeCode;}
	public void setLocaleCode(String localeCode) {this.localeCode = localeCode;}
	
	private UtilsFacade fJeesl;
	@Override public UtilsFacade getJeeslFacade() {return fJeesl;}
	public void setJeeslFacade(UtilsFacade fJeesl) {this.fJeesl = fJeesl;}
	
	private String symbolizerUrlPrefix;
	@Override public String getSymbolizerUrlPrefix() {return symbolizerUrlPrefix;}
	public void setSymbolizerUrlPrefix(String symbolizerUrlPrefix) {this.symbolizerUrlPrefix = symbolizerUrlPrefix;}
}