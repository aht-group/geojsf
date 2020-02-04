package org.geojsf.interfaces.model.core;

import java.io.Serializable;
import java.util.List;

import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfMap<L extends JeeslLang, D extends JeeslDescription,
						CATEGORY extends GeoJsfCategory<L,D,?>,
						VIEW extends GeoJsfView<?,?,VIEW>,
						VP extends GeoJsfViewPort
						>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbWithCode,EjbWithPositionVisible,
					EjbSaveable,
					EjbWithLang<L>,EjbWithDescription<D>
{	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	List<VIEW> getViews();
	void setViews(List<VIEW> views);
	
	VP getViewPort();
	void setViewPort(VP viewPort);
}