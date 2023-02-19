package org.geojsf.util;

import org.jeesl.interfaces.facade.JeeslFacade;

public class GeoJsfSldConfigurationProvider implements SldConfigurationProvider
{
	private static final long serialVersionUID = 1L;
	
	private String localeCode;
	@Override public String getLocaleCode() {return localeCode;}
	public void setLocaleCode(String localeCode) {this.localeCode = localeCode;}
	
	private JeeslFacade fJeesl;
	@Override public JeeslFacade getFacade() {return fJeesl;}
	public void setFacade(JeeslFacade fJeesl) {this.fJeesl = fJeesl;}
	
	private String symbolizerUrlPrefix;
	@Override public String getSymbolizerUrlPrefix() {return symbolizerUrlPrefix;}
	public void setSymbolizerUrlPrefix(String symbolizerUrlPrefix) {this.symbolizerUrlPrefix = symbolizerUrlPrefix;}
}