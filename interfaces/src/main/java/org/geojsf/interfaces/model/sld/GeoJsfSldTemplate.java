package org.geojsf.interfaces.model.sld;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface GeoJsfSldTemplate<L extends JeeslLang,
						   D extends JeeslDescription,
						   SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
						   SLDTYPE extends JeeslStatus<L,D,SLDTYPE>>
			extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{		
	String getXml();
	void setXml(String xml);
}