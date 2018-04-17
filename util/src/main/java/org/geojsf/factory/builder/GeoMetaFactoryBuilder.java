package org.geojsf.factory.builder;

import org.geojsf.factory.ejb.meta.EjbGeoViewPortFactory;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class GeoMetaFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									DS extends GeoJsfDataSource<L,D,?>,
									VP extends GeoJsfViewPort

										>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoMetaFactoryBuilder.class);
	
	private final Class<DS> cDs; public Class<DS> getClassDs() {return cDs;}
	private final Class<VP> cViewPort; public Class<VP> getcViewPort() {return cViewPort;}

	public GeoMetaFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<DS> cDs, final Class<VP> cViewPort)
	{
		super(cL,cD);
		this.cDs=cDs;
		
		this.cViewPort=cViewPort;
	}
	
    public EjbGeoViewPortFactory<VP> ejbViewPort()
	{
	    return new EjbGeoViewPortFactory<VP>(cViewPort);
	}
}