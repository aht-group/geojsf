package net.sf.geojsf.model.interfaces.openlayers;

import java.util.List;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface GeoJsfView<L extends UtilsLang,
						D extends UtilsDescription,
						LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
						SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "geoJsfView";
	
	List<LAYER> getLayer();
	void setLayer(List<LAYER> layer);
	
}