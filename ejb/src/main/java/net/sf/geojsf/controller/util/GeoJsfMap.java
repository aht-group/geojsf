package net.sf.geojsf.controller.util;

import java.util.List;

import net.sf.ahtutils.jsf.interfaces.dm.DmSingleSelect;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.primefaces.PrimefacesEjbIdDataModel;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GeoJsfMap <L extends UtilsLang,
						D extends UtilsDescription,
						LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
						VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
						SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
			implements DmSingleSelect<LAYER>
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMap.class);
	
	private GeoJsfMapLayerFactory<L,D,LAYER,VIEW,SERVICE> fMapLayer;
	
	private List<SERVICE> layerServices;
	private VIEW view;
	private PrimefacesEjbIdDataModel<LAYER> dmLayer;

	private GeoJsfMap(final Class<SERVICE> clService)
    {
    	fMapLayer = GeoJsfMapLayerFactory.factory(clService);
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
    GeoJsfMap<L,D,LAYER,VIEW,SERVICE> factory(final Class<SERVICE> clService,VIEW view)
    {
    	GeoJsfMap<L,D,LAYER,VIEW,SERVICE> f = new GeoJsfMap<L,D,LAYER,VIEW,SERVICE>(clService);
    	f.setView(view);
    	f.buildDmLayer();f.selectAll();
    	f.buildServices();
    	return f;
    }
    
    private void buildDmLayer()
    {
    	dmLayer = new PrimefacesEjbIdDataModel<LAYER>(view.getLayer());
    	dmLayer.setSingleSelectCallback(this);
    }
    
    private void selectAll()
    {
    	dmLayer.selectAll(true);
    }
    
    @Override public void dmSingleSelected(LAYER t)
    {
		this.buildServices();
		debug();
	}
    
    public void buildServices()
    {
    	layerServices = fMapLayer.build(dmLayer.toSelection());
    }
    
    public void debug()
    {
    	logger.info("DEBUG");
    	for(SERVICE service : layerServices)
    	{
    		logger.info(service.getCode());
    		for(LAYER layer : service.getLayer())
    		{
    			logger.info("\t"+layer.getCode());
    		}
    	}
    }
	
    public VIEW getView() {return view;}
	public void setView(VIEW view) {this.view = view;} 
    
    public PrimefacesEjbIdDataModel<LAYER> getDmLayer() {return dmLayer;}
    public List<SERVICE> getLayerServices() {return layerServices;}

}
