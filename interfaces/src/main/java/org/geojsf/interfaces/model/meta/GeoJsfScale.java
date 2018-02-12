package org.geojsf.interfaces.model.meta;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoJsfScale<L extends UtilsLang,D extends UtilsDescription>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbSaveable,EjbWithLangDescription<L,D>
{	
	int getValue();
	void setValue(int value);
}