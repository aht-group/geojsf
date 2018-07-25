package org.geojsf.interfaces.provider;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public interface SldConfigurationProvider
{
	String getLocaleCode();
	UtilsFacade getJeeslFacade();
	String getSymbolizerUrlPrefix();
}