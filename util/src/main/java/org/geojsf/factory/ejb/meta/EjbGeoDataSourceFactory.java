package org.geojsf.factory.ejb.meta;

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
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbGeoDataSourceFactory<L extends UtilsLang,D extends UtilsDescription,
G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
								SCALE extends GeoJsfScale<L,D>, 
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort,
								DS extends GeoJsfDataSource<L,D,LAYER>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
								RULE extends GeoJsfSldRule<L,D,G>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoDataSourceFactory.class);
	
	final Class<DS> cDs;
	private EjbLangFactory<L> fLang;
	private EjbDescriptionFactory<D> efDescription;
        
    public EjbGeoDataSourceFactory(final Class<L> cL, final Class<D> cD, final Class<DS> cDs)
    {
    	fLang = EjbLangFactory.factory(cL);
    	efDescription = EjbDescriptionFactory.factory(cD);
        this.cDs = cDs;
    }
	
	public DS build(String[] langKeys) throws UtilsConstraintViolationException
	{
		DS ejb;
		try
		{
			ejb = cDs.newInstance();
			ejb.setName(fLang.createEmpty(langKeys));
			ejb.setDescription(efDescription.createEmpty(langKeys));
		}
		catch (InstantiationException e) {throw new UtilsConstraintViolationException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsConstraintViolationException(e.getMessage());}
        return ejb;
    }
}