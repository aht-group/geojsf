package net.sf.geojsf.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;

public interface GeoJsfFacade 
{	
	<L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>, SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
		List<SERVICE> buildMapLayer(Class<VIEW> cView, VIEW view);
	
	<L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>, SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
		VIEW load(Class<VIEW> cView, VIEW view);
	
	<L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>, SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
		SERVICE load(Class<SERVICE> cService, SERVICE service);
	
	<L extends UtilsLang, D extends UtilsDescription, LAYER extends GeoJsfLayer<L, D, LAYER, SERVICE>, VIEW extends GeoJsfView<L, D, LAYER, SERVICE>, SERVICE extends GeoJsfService<L, D, LAYER, SERVICE>>
		LAYER load(Class<LAYER> cLayer, LAYER layer);
}
