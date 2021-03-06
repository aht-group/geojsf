package org.geojsf.interfaces.model.meta;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLangDescription;

public interface GeoJsfScale<L extends JeeslLang,D extends JeeslDescription>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbSaveable,EjbWithLangDescription<L,D>
{	
	int getValue();
	void setValue(int value);
}