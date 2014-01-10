package org.geojsf.controller.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.jsf.interfaces.dm.DmSingleSelect;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.primefaces.PrimefacesEjbIdDataModel;

import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GeoJsfMapHelper <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VL>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VL>, VL extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VL>>
			implements DmSingleSelect<LAYER>
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMapHelper.class);
	
	private GeoJsfServiceFactory<L,D,SERVICE,LAYER,MAP,VL> fMapLayer;
	
	private List<SERVICE> layerServices;
	private MAP view;
	private PrimefacesEjbIdDataModel<LAYER> dmLayer;

	private GeoJsfMapHelper(final Class<SERVICE> clService)
    {
    	fMapLayer = GeoJsfServiceFactory.factory(clService);
    	layerServices = new ArrayList<SERVICE>();
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VL>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VL>, VL extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VL>>
    	GeoJsfMapHelper<L,D,SERVICE,LAYER,MAP,VL> factory(final Class<SERVICE> clService,MAP view)
    {
    	GeoJsfMapHelper<L,D,SERVICE,LAYER,MAP,VL> gjm = new GeoJsfMapHelper<L,D,SERVICE,LAYER,MAP,VL>(clService);
    	gjm.setView(view);
    	gjm.buildDmLayer();gjm.selectAll();
    	gjm.buildServices();
    	return gjm;
    }
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VL>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VL>, VL extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VL>>
	GeoJsfMapHelper<L,D,SERVICE,LAYER,MAP,VL> build(final Class<SERVICE> clService, final Class<MAP> clView, final Class<VL> clVl, LAYER layer)
	{
    	MAP view = null;
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
    		logger.info(service.getId()+" "+service.getCode()+" "+service.getUrl());
    		for(LAYER layer : service.getLayer())
    		{
    			logger.info("\t"+layer.getId()+" "+layer.getCode());
    		}
    	}
    	logger.info("--- DEBUG END ---");
    }
	
    public MAP getView() {return view;}
	public void setView(MAP view) {this.view = view;} 
    
    public PrimefacesEjbIdDataModel<LAYER> getDmLayer() {return dmLayer;}
    public List<SERVICE> getLayerServices() {return layerServices;}
}