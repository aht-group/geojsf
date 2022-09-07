package org.geojsf.interfaces.model.core;

import java.io.Serializable;
import java.util.List;

import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisible;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslAttributes;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslDescription;

@DownloadJeeslDescription
@DownloadJeeslAttributes
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