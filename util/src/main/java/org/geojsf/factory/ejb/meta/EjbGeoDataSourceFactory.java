package org.geojsf.factory.ejb.meta;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbGeoDataSourceFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,?,?,DS,?>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,?>,
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								DS extends GeoJsfDataSource<L,D,LAYER>>
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