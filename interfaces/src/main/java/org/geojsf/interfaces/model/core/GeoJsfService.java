package org.geojsf.interfaces.model.core;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfService<L extends JeeslLang,D extends JeeslDescription,
								
								LAYER extends GeoJsfLayer<L,D,?,?,?,?,?>
								>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{		
	String getWms();
	void setWms(String wms);
	
	String getWcs();
	void setWcs(String wcs);
	
	List<LAYER> getLayer();
	void setLayer(List<LAYER> layer);
}