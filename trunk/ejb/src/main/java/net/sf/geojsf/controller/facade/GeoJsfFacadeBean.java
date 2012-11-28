package net.sf.geojsf.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.controller.interfaces.GeoJsfFacade;
import net.sf.geojsf.controller.util.GeoJsfMapLayerFactory;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;

public class GeoJsfFacadeBean implements GeoJsfFacade
{	
	private EntityManager em;
	
	public GeoJsfFacadeBean(EntityManager em)
	{
		this.em=em;
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, LAYER extends GeoJsfLayer<L, D, LAYER, SERVICE>, VIEW extends GeoJsfView<L, D, LAYER, SERVICE>, SERVICE extends GeoJsfService<L, D, LAYER, SERVICE>>
		List<SERVICE> buildMapLayer(Class<VIEW> cView, VIEW view)
	{
		view = em.find(cView, view.getId());
		
		GeoJsfMapLayerFactory<L,D,LAYER,VIEW,SERVICE> fJsf = null;
		fJsf = new GeoJsfMapLayerFactory<L,D,LAYER,VIEW,SERVICE>(null);
		return fJsf.build(view);
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, LAYER extends GeoJsfLayer<L, D, LAYER, SERVICE>, VIEW extends GeoJsfView<L, D, LAYER, SERVICE>, SERVICE extends GeoJsfService<L, D, LAYER, SERVICE>>
		VIEW load(Class<VIEW> cView, VIEW view)
	{
		view = em.find(cView, view.getId());
		view.getLayer().size();
		return view;
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, LAYER extends GeoJsfLayer<L, D, LAYER, SERVICE>, VIEW extends GeoJsfView<L, D, LAYER, SERVICE>, SERVICE extends GeoJsfService<L, D, LAYER, SERVICE>>
		SERVICE load(Class<SERVICE> cService, SERVICE service)
	{
		service = em.find(cService, service.getId());
		service.getLayer().size();
		return service;
	}
	
	@Override
	public <L extends UtilsLang, D extends UtilsDescription, LAYER extends GeoJsfLayer<L, D, LAYER, SERVICE>, VIEW extends GeoJsfView<L, D, LAYER, SERVICE>, SERVICE extends GeoJsfService<L, D, LAYER, SERVICE>>
		LAYER load(Class<LAYER> cLayer, LAYER layer)
	{
		layer = em.find(cLayer, layer.getId());
		return layer;
	}
}
