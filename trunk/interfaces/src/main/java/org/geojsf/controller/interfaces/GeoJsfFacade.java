package org.geojsf.controller.interfaces;

import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface GeoJsfFacade 
{		
	<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>, LT extends UtilsStatus<L,D>>
		VIEW load(Class<VIEW> cView, VIEW view);
	
	<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>, LT extends UtilsStatus<L,D>>
		SERVICE load(Class<SERVICE> cService, SERVICE service);
	
	<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>, LT extends UtilsStatus<L,D>>
		LAYER load(Class<LAYER> cLayer, LAYER layer);
	
	<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>, LT extends UtilsStatus<L,D>>
		void rm(Class<VL> cViewLayer, VL viewLayer);
}