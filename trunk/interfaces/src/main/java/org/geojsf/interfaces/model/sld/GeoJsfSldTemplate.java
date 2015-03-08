package org.geojsf.interfaces.model.sld;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface GeoJsfSldTemplate<L extends UtilsLang,
						   D extends UtilsDescription,
						   SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
						   SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
			extends EjbSaveable,EjbRemoveable
{	
	SLDTYPE getType();
	void setType(SLDTYPE type);
}