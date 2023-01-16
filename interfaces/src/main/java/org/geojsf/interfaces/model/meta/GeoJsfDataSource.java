package org.geojsf.interfaces.model.meta;

import java.io.Serializable;
import java.util.List;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLangDescription;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslAttributes;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslDescription;

@DownloadJeeslDescription
@DownloadJeeslAttributes
public interface GeoJsfDataSource<L extends JeeslLang,D extends JeeslDescription,
									LAYER extends GeoJsfLayer<L,D,?,?,?,?,?>
									>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbSaveable,EjbWithLangDescription<L,D>
{
	List<LAYER> getLayers();
	void setLayers(List<LAYER> layers);
}