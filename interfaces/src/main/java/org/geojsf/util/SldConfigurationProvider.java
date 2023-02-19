package org.geojsf.util;

import java.io.Serializable;

import org.jeesl.interfaces.facade.JeeslFacade;

public interface SldConfigurationProvider extends Serializable
{
	String getLocaleCode();
	JeeslFacade getFacade();
	String getSymbolizerUrlPrefix();
	
//	void x();
}