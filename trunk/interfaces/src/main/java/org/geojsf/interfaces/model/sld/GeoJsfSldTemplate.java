package org.geojsf.interfaces.model.sld;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSldTemplate<L extends UtilsLang,
						   D extends UtilsDescription,
						   TYPE extends UtilsStatus<TYPE,L,D>,
						   STYLE extends UtilsStatus<STYLE,L,D>,
						   TEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,TEMPLATE>>
			extends EjbSaveable,EjbRemoveable,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{	
	TYPE getType();
	void setType(TYPE type);
}