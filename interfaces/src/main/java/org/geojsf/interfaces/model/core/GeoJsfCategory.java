package org.geojsf.interfaces.model.core;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfCategory<L extends UtilsLang, D extends UtilsDescription,
								LAYER extends GeoJsfLayer<L,D,?,?,?,?,?>>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,EjbWithCode,EjbWithPositionVisible,
					EjbWithLang<L>,EjbWithDescription<D>
{
	List<LAYER> getLayer();
	void setLayer(List<LAYER> layer);
}