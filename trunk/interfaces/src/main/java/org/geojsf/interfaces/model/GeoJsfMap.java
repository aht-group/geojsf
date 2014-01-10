package org.geojsf.interfaces.model;

import java.util.List;


import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfMap<L extends UtilsLang,
						D extends UtilsDescription,
						SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,
						LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,
						MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,
						VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "geoJsfMap";
	
	List<VIEW> getViews();
	void setViews(List<VIEW> views);
	
	Double getX();
	void setX(Double x);
	
	Double getY();
	void setY(Double y);
	
	Integer getZoom();
	void setZoom(Integer zoom);
}