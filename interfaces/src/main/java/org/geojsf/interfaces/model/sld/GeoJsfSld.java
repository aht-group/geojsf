package org.geojsf.interfaces.model.sld;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface GeoJsfSld<L extends UtilsLang,
						   D extends UtilsDescription,
						   T extends UtilsStatus<T,L,D>,
						   SLD extends GeoJsfSld<L,D,T,SLD>>
			extends EjbSaveable,EjbRemoveable
{	
	T getType();
	void setType(T type);
}