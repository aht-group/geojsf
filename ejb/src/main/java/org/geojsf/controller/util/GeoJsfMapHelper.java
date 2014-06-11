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

//REmove au-jsf from pom when deleting!!

@Deprecated
public class GeoJsfMapHelper <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
			implements DmSingleSelect<LAYER>
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMapHelper.class);
	
	private GeoJsfServiceFactory<L,D,SERVICE,LAYER,MAP,VIEW> fMapLayer;
	
	private List<SERVICE> layerServices;
	private MAP map;
	private PrimefacesEjbIdDataModel<LAYER> dmLayer;

	private GeoJsfMapHelper(final Class<SERVICE> clService)
    {
    	fMapLayer = GeoJsfServiceFactory.factory(clService);
    	layerServices = new ArrayList<SERVICE>();
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
    	GeoJsfMapHelper<L,D,SERVICE,LAYER,MAP,VIEW> factory(final Class<SERVICE> clService,MAP view)
    {
    	GeoJsfMapHelper<L,D,SERVICE,LAYER,MAP,VIEW> gjm = new GeoJsfMapHelper<L,D,SERVICE,LAYER,MAP,VIEW>(clService);
    	gjm.setMap(view);
    	gjm.buildDmLayer();gjm.selectAll();
    	gjm.buildServices();
    	return gjm;
    }
    
    @Deprecated
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
	GeoJsfMapHelper<L,D,SERVICE,LAYER,MAP,VIEW> build(final Class<SERVICE> clService, final Class<MAP> clMap, final Class<VIEW> clVl, LAYER layer)
	{
    	MAP map = null;
    	VIEW view = null;
		try
		{
			map = clMap.newInstance();
			view = clVl.newInstance();
			view.setLayer(layer);
			view.setOrderNo(1);
			view.setMap(map);
			view.setVisible(true);
			map.getViews().add(view);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return factory(clService,map);
	}
    
    private void buildDmLayer()
    {
    	List<LAYER> list = new ArrayList<LAYER>();
    	for(VIEW vl: map.getViews())
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
    	for(VIEW view : map.getViews())
		{
			logger.info("vl.layer="+view.getLayer().getCode()+"."+view.getLayer().getService().getCode());
			if(dmLayer.isSelected(view.getLayer().getId()))
			{
				layers.add(view.getLayer());
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
	
    public MAP getMap() {return map;}
	public void setMap(MAP view) {this.map = view;} 
    
    public PrimefacesEjbIdDataModel<LAYER> getDmLayer() {return dmLayer;}
    public List<SERVICE> getLayerServices() {return layerServices;}
}