package org.geojsf.interfaces.model.sld;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSldTemplate<L extends UtilsLang,
						   D extends UtilsDescription,
						   SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
						   SLDTYPE extends UtilsStatus<SLDTYPE,L,D>>
			extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{		
	String getXml();
	void setXml(String xml);
}