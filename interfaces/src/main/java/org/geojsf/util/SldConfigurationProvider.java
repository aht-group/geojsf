package org.geojsf.util;

import org.jeesl.interfaces.facade.JeeslFacade;

public interface SldConfigurationProvider
{
	String getLocaleCode();
	JeeslFacade getJeeslFacade();
	String getSymbolizerUrlPrefix();
}