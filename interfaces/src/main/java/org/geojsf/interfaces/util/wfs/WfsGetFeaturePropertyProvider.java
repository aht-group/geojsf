package org.geojsf.interfaces.util.wfs;

import org.jdom2.Namespace;

//jeesl.highlight:interface
public interface WfsGetFeaturePropertyProvider
{
	String getWorkspace();
	Namespace getNameSpace();
//	String getGeoServerRestUrl();
}
//jeesl.highlight:interface