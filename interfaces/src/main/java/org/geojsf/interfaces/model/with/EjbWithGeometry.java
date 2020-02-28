package org.geojsf.interfaces.model.with;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithGeometry <T extends Object> extends EjbWithId
{
	T getGeometry();
	void setGeometry(T geometry);
}