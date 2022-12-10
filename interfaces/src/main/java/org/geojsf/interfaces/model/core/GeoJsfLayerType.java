package org.geojsf.interfaces.model.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslAttributes;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslData;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslDescription;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

@DownloadJeeslAttributes
@DownloadJeeslDescription
@DownloadJeeslData
public interface GeoJsfLayerType<L extends JeeslLang, D extends JeeslDescription,S extends JeeslStatus<L,D,S>,G extends JeeslGraphic<?,?,?>>
						extends Serializable,EjbPersistable,
								JeeslStatus<L,D,S>,JeeslStatusFixedCode,
								EjbWithCodeGraphic<G>
{
	public enum Code {pg,pgSql,shapeFile,shapeDir}
}