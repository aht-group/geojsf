package org.geojsf.interfaces.model.core;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisible;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslAttributes;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslDescription;

@DownloadJeeslDescription
@DownloadJeeslAttributes
public interface GeoJsfCategory<L extends JeeslLang, D extends JeeslDescription,
								LAYER extends GeoJsfLayer<L,D,?,?,?,?,?,?>>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,EjbWithCode,EjbWithPositionVisible,
					EjbWithLang<L>,EjbWithDescription<D>
{
	List<LAYER> getLayer();
	void setLayer(List<LAYER> layer);
}