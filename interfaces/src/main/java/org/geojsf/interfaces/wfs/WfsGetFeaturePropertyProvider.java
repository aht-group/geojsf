package org.geojsf.interfaces.wfs;

import java.util.List;

import org.jdom2.Namespace;

//ahtutils.highlight:interface
public interface WfsGetFeaturePropertyProvider
{
	List<String> getProperties(Class<?> type);
	String getWorkspace();
	Namespace getNameSpace();
}
//ahtutils.highlight:interface