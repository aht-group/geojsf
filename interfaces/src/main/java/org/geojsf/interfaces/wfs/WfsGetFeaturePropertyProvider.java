package org.geojsf.interfaces.wfs;

import java.util.List;

import org.jdom2.Namespace;

//ahtutils.highlight:interface
public interface WfsGetFeaturePropertyProvider
{
	String getWorkspace();
	String getGeoServerRestUrl();
	Namespace getNameSpace();

	List<String> getProperties(Class<?> type);
}
//ahtutils.highlight:interface