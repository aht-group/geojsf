package org.geojsf.interfaces.wfs;

import java.util.List;

public interface WfsGetFeaturePropertyProvider
{
	List<String> getProperties(Class<?> clazz);
}