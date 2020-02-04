package org.geojsf.interfaces.model.meta;

import java.io.Serializable;
import java.util.List;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface GeoJsfDataSource<L extends JeeslLang,D extends JeeslDescription,
									LAYER extends GeoJsfLayer<L,D,?,?,?,?,?>
									>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbSaveable,EjbWithLangDescription<L,D>
{
	List<LAYER> getLayers();
	void setLayers(List<LAYER> layers);
}