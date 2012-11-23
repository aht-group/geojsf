package net.sf.geojsf.controller.util;


public class GeoJsfMap{} /*<L extends UtilsLang,
						D extends UtilsDescription,
						LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
						VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
						SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMap.class);
	
	private GeoJsfMapLayerFactory<L,D,LAYER,VIEW,SERVICE> fMapLayer;
	
	private List<SERVICE> layerServices;
	private VIEW view;
	private PrimefacesEjbIdDataModel<LAYER> dmLayer;

	private GeoJsfMap()
    {
    	fMapLayer = GeoJsfMapLayerFactory.factory();
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
    GeoJsfMap<L,D,LAYER,VIEW,SERVICE> factory(VIEW view)
    {
    	GeoJsfMap<L,D,LAYER,VIEW,SERVICE> f = new GeoJsfMap<L,D,LAYER,VIEW,SERVICE>();
    	f.setView(view);
    	f.buildDmLayer();
    	f.buildServices();
    	return f;
    }
    
    private void buildDmLayer()
    {
    	dmLayer = new PrimefacesEjbIdDataModel<LAYER>(view.getLayer());
    }
    
    public void buildServices()
    {
    	layerServices = fMapLayer.build(dmLayer.toSelection());
    }
	
    public VIEW getView() {return view;}
	public void setView(VIEW view) {this.view = view;} 
    
    public PrimefacesEjbIdDataModel<LAYER> getDmLayer() {return dmLayer;}
    public List<SERVICE> getLayerServices() {return layerServices;}
}
*/