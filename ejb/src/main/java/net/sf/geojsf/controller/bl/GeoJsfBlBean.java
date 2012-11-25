package net.sf.geojsf.controller.bl;

import java.util.List;

import javax.persistence.EntityManager;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.controller.interfaces.GeoJsfBl;
import net.sf.geojsf.controller.util.GeoJsfMapLayerFactory;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;

public class GeoJsfBlBean implements GeoJsfBl
{	
	private EntityManager em;
	
	public GeoJsfBlBean(EntityManager em)
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
}
