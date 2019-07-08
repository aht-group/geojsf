package org.geojsf.interfaces.model.json;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;

public interface GeoJsfLocationLevel<S extends UtilsStatus<S,L,D>,
								L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,?,?,?>>
						extends Serializable,EjbPersistable,
								
								UtilsStatus<S,L,D>,UtilsStatusFixedCode,
								EjbWithCodeGraphic<G>
{
	
}