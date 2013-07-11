package org.geojsf.interfaces.wfs;

import java.util.List;

import org.jdom2.Namespace;

public interface WfsGetFeaturePropertyProvider
{
	List<String> getProperties(Class<?> clazz);
	String getWorkspace();
	Namespace getNameSpace();
}