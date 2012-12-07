package net.sf.geojsf.db.export;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.controller.factory.xml.openlayers.XmlLayerFactory;
import net.sf.geojsf.controller.factory.xml.openlayers.XmlServiceFactory;
import net.sf.geojsf.controller.interfaces.GeoJsfExportRest;
import net.sf.geojsf.controller.util.query.OpenLayersQuery;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import net.sf.geojsf.xml.openlayers.Layers;
import net.sf.geojsf.xml.openlayers.Repository;
import net.sf.geojsf.xml.openlayers.Views;

public class GeoJsfDbRestExport <L extends UtilsLang,
									D extends UtilsDescription,
									SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
									LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
									VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,
									VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
				implements GeoJsfExportRest
{
	private UtilsSecurityFacade fSecurity;
	
	private final Class<SERVICE> cService;
	private final Class<LAYER> cLayer;
	
	private GeoJsfDbRestExport(UtilsSecurityFacade fSecurity,final Class<SERVICE> cService,final Class<LAYER> cLayer)
	{
		this.fSecurity=fSecurity;
		this.cService=cService;
		this.cLayer=cLayer;
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,
					LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,
					VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,
					VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		GeoJsfDbRestExport<L,D,SERVICE,LAYER,VIEW,VL>
		factory(UtilsSecurityFacade fSecurity, final Class<SERVICE> cService,final Class<LAYER> cLayer)
	{
		return new GeoJsfDbRestExport<L,D,SERVICE,LAYER,VIEW,VL>(fSecurity,cService,cLayer);
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

		return views;
	}
}
