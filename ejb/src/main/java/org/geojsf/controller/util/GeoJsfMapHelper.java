package org.geojsf.controller.util;

import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.geojsf.GeoJsfServiceFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.interfaces.dm.DmSingleSelect;
import net.sf.ahtutils.model.primefaces.PrimefacesEjbIdDataModel;

//REmove au-jsf from pom when deleting!!

@Deprecated
public class GeoJsfMapHelper <L extends UtilsLang,D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
								CATEGORY extends GeoJsfCategory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								SERVICE extends GeoJsfService<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								LAYER extends GeoJsfLayer<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								MAP extends GeoJsfMap<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								SCALE extends GeoJsfScale<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, 
								VIEW extends GeoJsfView<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								VP extends GeoJsfViewPort<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								DS extends GeoJsfDataSource<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLD extends GeoJsfSld<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								RULE extends GeoJsfSldRule<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
	implements DmSingleSelect<LAYER>
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMapHelper.class);
	
	private GeoJsfServiceFactory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fMapLayer;
	
	private List<SERVICE> layerServices;
	private MAP map;
	private PrimefacesEjbIdDataModel<LAYER> dmLayer;

	private GeoJsfMapHelper(final Class<SERVICE> clService)
    {
    	fMapLayer = GeoJsfServiceFactory.factory(clService);
    	layerServices = new ArrayList<SERVICE>();
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,
				    G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SERVICE extends GeoJsfService<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					LAYER extends GeoJsfLayer<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					MAP extends GeoJsfMap<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, SCALE extends GeoJsfScale<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, 
					VIEW extends GeoJsfView<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					VP extends GeoJsfViewPort<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					DS extends GeoJsfDataSource<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					SLD extends GeoJsfSld<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					RULE extends GeoJsfSldRule<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
    	GeoJsfMapHelper<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
    	factory(final Class<SERVICE> clService,MAP view)
    {
    	GeoJsfMapHelper<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> gjm = new GeoJsfMapHelper<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(clService);
    	gjm.setMap(view);
    	gjm.buildDmLayer();gjm.selectAll();
    	gjm.buildServices();
    	return gjm;
    }
    
    @Deprecated
    public static <L extends UtilsLang,D extends UtilsDescription,
				    G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SERVICE extends GeoJsfService<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					LAYER extends GeoJsfLayer<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					MAP extends GeoJsfMap<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, SCALE extends GeoJsfScale<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, 
					VIEW extends GeoJsfView<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					VP extends GeoJsfViewPort<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					DS extends GeoJsfDataSource<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					SLD extends GeoJsfSld<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					RULE extends GeoJsfSldRule<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
    	GeoJsfMapHelper<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
    build(final Class<SERVICE> clService, final Class<MAP> clMap, final Class<VIEW> clVl, LAYER layer)
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
    		logger.info(service.getId()+" "+service.getCode()+" "+service.getWms());
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