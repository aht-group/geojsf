package org.geojsf.controller.util;

import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.interfaces.facade.JeeslFacade;

public class GeoJsfSldConfigurationProvider implements SldConfigurationProvider
{
	private String localeCode;
	@Override public String getLocaleCode() {return localeCode;}
	public void setLocaleCode(String localeCode) {this.localeCode = localeCode;}
	
	private JeeslFacade fJeesl;
	@Override public JeeslFacade getJeeslFacade() {return fJeesl;}
	public void setJeeslFacade(JeeslFacade fJeesl) {this.fJeesl = fJeesl;}
	
	private String symbolizerUrlPrefix;
	@Override public String getSymbolizerUrlPrefix() {return symbolizerUrlPrefix;}
	public void setSymbolizerUrlPrefix(String symbolizerUrlPrefix) {this.symbolizerUrlPrefix = symbolizerUrlPrefix;}
}