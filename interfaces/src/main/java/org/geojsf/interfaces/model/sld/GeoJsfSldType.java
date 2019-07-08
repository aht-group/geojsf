package org.geojsf.interfaces.model.sld;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;

public interface GeoJsfSldType<S extends UtilsStatus<S,L,D>,
								L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,?,?,?>>
						extends Serializable,EjbPersistable,
								JeeslOptionRestDownload,
								UtilsStatus<S,L,D>,UtilsStatusFixedCode,
								EjbWithCodeGraphic<G>
{
	public static enum Type{rule,status,template}
}