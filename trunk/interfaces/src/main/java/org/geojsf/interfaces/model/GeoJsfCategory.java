package org.geojsf.interfaces.model;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfCategory<L extends UtilsLang,
								D extends UtilsDescription,
								CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,
								SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW,VP>,
								LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW,VP>,
								MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW,VP>,
								VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW,VP>,
								VP extends GeoJsfViewPort<L,D,SERVICE,LAYER,MAP,VIEW,VP>>
			extends EjbSaveable,EjbRemoveable,
					EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "geoJsfCategory";
/*	
	List<LAYER> getLayer();
	void setLayer(List<LAYER> layer);
*/
}