package net.sf.geojsf.util.factory.ejb.openlayer;

import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoLayerFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,LT extends UtilsStatus<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoLayerFactory.class);
	
	final Class<LAYER> clLayer;
	private EjbLangFactory<L> fLang;
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,LT extends UtilsStatus<L,D>>
    	EjbGeoLayerFactory<L,D,SERVICE,LAYER,VIEW,VL,LT> factory(final Class<L> cLang,final Class<LAYER> clLayer)
    {
        return new EjbGeoLayerFactory<L,D,SERVICE,LAYER,VIEW,VL,LT>(cLang,clLayer);
    }
    
    public EjbGeoLayerFactory(final Class<L> cLang, final Class<LAYER> clLayer)
    {
    	fLang = EjbLangFactory.createFactory(cLang);
        this.clLayer = clLayer;
    } 
	
	public LAYER create(String code, SERVICE service, String[] langKeys) throws UtilsIntegrityException
	{
		LAYER ejb;
		try
		{
			ejb = clLayer.newInstance();
			ejb.setName(fLang.createEmpty(langKeys));
		}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setCode(code);
		ejb.setService(service);
        return ejb;
    }
}