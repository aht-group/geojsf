package org.geojsf.controller.util;

import org.geojsf.interfaces.provider.SldConfigurationProvider;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public class GeoJsfSldConfigurationProvider implements SldConfigurationProvider
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