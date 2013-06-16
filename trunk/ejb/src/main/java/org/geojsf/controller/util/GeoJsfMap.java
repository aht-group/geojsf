package org.geojsf.controller.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.jsf.interfaces.dm.DmSingleSelect;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.primefaces.PrimefacesEjbIdDataModel;

import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GeoJsfMap <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
			implements DmSingleSelect<LAYER>
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMap.class);
	
	private GeoJsfServiceFactory<L,D,SERVICE,LAYER,VIEW,VL> fMapLayer;
	
	private List<SERVICE> layerServices;
	private VIEW view;
	private PrimefacesEjbIdDataModel<LAYER> dmLayer;

	private GeoJsfMap(final Class<SERVICE> clService)
    {
    	fMapLayer = GeoJsfServiceFactory.factory(clService);
    	layerServices = new ArrayList<SERVICE>();
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
    	GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL> factory(final Class<SERVICE> clService,VIEW view)
    {
    	GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL> f = new GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL>(clService);
    	f.setView(view);
    	f.buildDmLayer();f.selectAll();
    	f.buildServices();
    	return f;
    }
    
    private void buildDmLayer()
    {
    	List<LAYER> list = new ArrayList<LAYER>();
    	for(VL vl: view.getLayer())
    	{
    		list.add(vl.getLayer());
    	}
    	dmLayer = new PrimefacesEjbIdDataModel<LAYER>(list);
    	dmLayer.setSingleSelectCallback(this);
    }
    
    private void selectAll()
    {
    	dmLayer.selectAll(true);
    }
    
    @Override public void dmSingleSelected(LAYER t)
    {
    	logger.info("dmSingleSelected "+t);
    	logger.info("dmLayer.size="+dmLayer.getRowCount());
		this.buildServices();
		debug();
	}
    
    public void buildServices()
    {
    	layerServices.clear();
    	List<LAYER> layers = new ArrayList<LAYER>();
    	int size = view.getLayer().size();
    	for(int i=(size-1);i>=0;i--)
		{
			VL vl = view.getLayer().get(i);
			logger.info("vl.layer="+vl.getLayer().getCode()+"."+vl.getLayer().getService().getCode());
			if(dmLayer.isSelected(vl.getLayer().getId()))
			{
				layers.add(vl.getLayer());
			}
		}
    	logger.info("Will build services for "+layers.size()+" layers");
    	layerServices.addAll(fMapLayer.buildFromLayer(layers));
    	logger.info("Just build "+layerServices.size()+" services");
    }
    
    public void debug()
    {
    	logger.info("--- DEBUG START ---");
    	for(SERVICE service : layerServices)
    	{
    		logger.info(service.getCode()+" "+service.getUrl());
    		for(LAYER layer : service.getLayer())
    		{
    			logger.info("\t"+layer.getCode());
    		}
    	}
    	logger.info("--- DEBUG END ---");
    }
	
    public VIEW getView() {return view;}
	public void setView(VIEW view) {this.view = view;} 
    
    public PrimefacesEjbIdDataModel<LAYER> getDmLayer() {return dmLayer;}
    public List<SERVICE> getLayerServices() {return layerServices;}
}