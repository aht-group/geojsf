package net.sf.geojsf.model.interfaces.openlayers;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface GeoJsfLayer<L extends UtilsLang,
							D extends UtilsDescription,
							SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
							LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
							VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,
							VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "geoJsfLayer";
	
	SERVICE getService();
	void setService(SERVICE service);
}