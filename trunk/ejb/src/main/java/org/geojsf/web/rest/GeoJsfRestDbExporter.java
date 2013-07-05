package org.geojsf.web.rest;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.controller.util.query.OpenLayersQuery;
import org.geojsf.factory.xml.openlayers.XmlLayerFactory;
import org.geojsf.factory.xml.openlayers.XmlServiceFactory;
import org.geojsf.factory.xml.openlayers.XmlViewFactory;
import org.geojsf.interfaces.model.openlayers.GeoJsfLayer;
import org.geojsf.interfaces.model.openlayers.GeoJsfService;
import org.geojsf.interfaces.model.openlayers.GeoJsfView;
import org.geojsf.interfaces.model.openlayers.GeoJsfViewLayer;
import org.geojsf.interfaces.rest.GeoJsfRestExport;
import org.geojsf.xml.openlayers.Layers;
import org.geojsf.xml.openlayers.Repository;
import org.geojsf.xml.openlayers.View;
import org.geojsf.xml.openlayers.Views;

public class GeoJsfRestDbExporter <L extends UtilsLang,
									D extends UtilsDescription,
									SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
									LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
									VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,
									VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
				implements GeoJsfRestExport
{
	private UtilsSecurityFacade fSecurity;
	
	private final Class<SERVICE> cService;
	private final Class<LAYER> cLayer;
	private final Class<VIEW> cView;
	
	private GeoJsfRestDbExporter(UtilsSecurityFacade fSecurity,final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<VIEW> cView)
	{
		this.fSecurity=fSecurity;
		this.cService=cService;
		this.cLayer=cLayer;
		this.cView=cView;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
					LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
					VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,
					VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>,
					LT extends UtilsStatus<L,D>>
		GeoJsfRestDbExporter<L,D,SERVICE,LAYER,VIEW,VL>
		factory(UtilsSecurityFacade fSecurity, final Class<SERVICE> cService,final Class<LAYER> cLayer,final Class<VIEW> cView)
	{
		return new GeoJsfRestDbExporter<L,D,SERVICE,LAYER,VIEW,VL>(fSecurity,cService,cLayer,cView);
	}

	@Override
	public Repository exportServices()
	{
		Repository repository = new Repository();
		XmlServiceFactory f = new XmlServiceFactory(OpenLayersQuery.get(OpenLayersQuery.Key.service, null));
		
		for(SERVICE service : fSecurity.all(cService))
		{
			repository.getService().add(f.build(service));
		}
		
		return repository;
	}

	@Override
	public Layers exportLayers()
	{
		Layers layers = new Layers();
		XmlLayerFactory f = new XmlLayerFactory(OpenLayersQuery.get(OpenLayersQuery.Key.layer, null));
		
		for(LAYER layer : fSecurity.all(cLayer))
		{
			layers.getLayer().add(f.build(layer));
		}
		
		return layers;
	}

	@Override
	public Views exportViews()
	{
		Views views = new Views();
		XmlViewFactory fView = new XmlViewFactory(OpenLayersQuery.get(OpenLayersQuery.Key.view, null));
		XmlLayerFactory fLayer = new XmlLayerFactory(OpenLayersQuery.get(OpenLayersQuery.Key.viewLayer, null));
		
		for(VIEW ejb : fSecurity.all(cView))
		{
			View xml = fView.build(ejb);
			
			for(VL vl : ejb.getLayer())
			{
				xml.getLayer().add(fLayer.build(vl));
			}
			
			views.getView().add(xml);
		}
		
		return views;
	}
}