package org.geojsf.interfaces.wfs;

import org.jdom2.Namespace;

//ahtutils.highlight:interface
public interface WfsGetFeaturePropertyProvider
{
	String getWorkspace();
	String getGeoServerRestUrl();
	Namespace getNameSpace();
}
//ahtutils.highlight:interface