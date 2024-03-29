package org.geojsf.util.wfs;

import org.geojsf.interfaces.util.wfs.WfsGetFeaturePropertyProvider;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfGetFeaturePropertyProvider implements WfsGetFeaturePropertyProvider
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfGetFeaturePropertyProvider.class);

	public GeoJsfGetFeaturePropertyProvider()
	{

	}

	@Override
	public String getWorkspace()
	{
		return "geojsf";
	}

	@Override
	public Namespace getNameSpace()
	{
		return Namespace.getNamespace("geojsf", "http://www.geojsf.org");
	}

}