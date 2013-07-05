package org.geojsf.controller.facade;

import javax.persistence.EntityManager;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.facade.GeoJsfFacade;
import org.geojsf.interfaces.model.openlayers.GeoJsfLayer;
import org.geojsf.interfaces.model.openlayers.GeoJsfService;
import org.geojsf.interfaces.model.openlayers.GeoJsfView;
import org.geojsf.interfaces.model.openlayers.GeoJsfViewLayer;

public class GeoJsfFacadeBean implements GeoJsfFacade
{	
	private EntityManager em;
	
	public GeoJsfFacadeBean(EntityManager em)
	{
		this.em=em;
	}

	@Override
	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		VIEW load(Class<VIEW> cView, VIEW view)
	{
		view = em.find(cView, view.getId());
		view.getLayer().size();
		return view;
	}

	@Override
	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		SERVICE load(Class<SERVICE> cService, SERVICE service)
	{
		service = em.find(cService, service.getId());
		service.getLayer().size();
		return service;
	}
	
	@Override
	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		LAYER load(Class<LAYER> cLayer, LAYER layer)
	{
		layer = em.find(cLayer, layer.getId());
		return layer;
	}

	@Override
	public <L extends UtilsLang, D extends UtilsDescription, SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		void rm(Class<VL> cViewLayer, VL viewLayer)
	{
		viewLayer = em.find(cViewLayer, viewLayer.getId());
		viewLayer.getView().getLayer().remove(viewLayer);
		em.remove(viewLayer);
	}
}
