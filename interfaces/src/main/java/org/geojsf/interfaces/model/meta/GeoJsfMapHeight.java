package org.geojsf.interfaces.model.meta;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;

public interface GeoJsfMapHeight <L extends JeeslLang, D extends JeeslDescription,
									S extends JeeslStatus<L,D,S>,
									G extends JeeslGraphic<?,?,?>>
					extends Serializable,EjbPersistable,
							JeeslStatus<L,D,S>,JeeslStatusFixedCode,
							EjbWithCodeGraphic<G>
{
	public static enum Code {h300, h400, h500}
}