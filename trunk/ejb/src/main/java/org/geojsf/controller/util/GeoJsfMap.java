package org.geojsf.controller.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.jsf.interfaces.dm.DmSingleSelect;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.primefaces.PrimefacesEjbIdDataModel;

import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.interfaces.model.openlayers.GeoJsfLayer;
import org.geojsf.interfaces.model.openlayers.GeoJsfService;
import org.geojsf.interfaces.model.openlayers.GeoJsfView;
import org.geojsf.interfaces.model.openlayers.GeoJsfViewLayer;
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
    	GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL> gjm = new GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL>(clService);
    	gjm.setView(view);
    	gjm.buildDmLayer();gjm.selectAll();
    	gjm.buildServices();
    	return gjm;
    }
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
	GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL> build(final Class<SERVICE> clService, final Class<VIEW> clView, final Class<VL> clVl, LAYER layer)
	{
    	VIEW view = null;
    	VL vl = null;
		try
		{
			
			view = clView.newInstance();
			vl = clVl.newInstance();
			vl.setLayer(layer);
			vl.setOrderNo(1);
			vl.setView(view);
			vl.setVisible(true);
			view.getLayer().add(vl);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return factory(clService,view);
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
    	for(VL vl : view.getLayer())
		{
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