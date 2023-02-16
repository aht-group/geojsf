package org.geojsf.factory.builder;

import java.util.Comparator;

import org.geojsf.factory.ejb.meta.EjbGeoDataSourceFactory;
import org.geojsf.factory.ejb.meta.EjbGeoScaleFactory;
import org.geojsf.factory.ejb.meta.EjbGeoViewPortFactory;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfEcql;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.util.comparator.ejb.GeoScaleComparator;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoMetaFactoryBuilder<L extends JeeslLang, D extends JeeslDescription,
									DS extends GeoJsfDataSource<L,D,?>,
									VP extends GeoJsfViewPort,
									SCALE extends GeoJsfScale<L,D>
									,ECQL extends GeoJsfEcql<?,?,?>
>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoMetaFactoryBuilder.class);
	
	private final Class<DS> cDs; public Class<DS> getClassDs() {return cDs;}
	private final Class<VP> cViewPort; public Class<VP> getcViewPort() {return cViewPort;}
	private final Class<SCALE> cScale; public Class<SCALE> getClassScale() {return cScale;}


	public GeoMetaFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<DS> cDs,
								final Class<VP> cViewPort,
								final Class<SCALE> cScale)
	{
		super(cL,cD);
		this.cDs=cDs;
		
		this.cViewPort=cViewPort;
		this.cScale=cScale;
	}
	
    public EjbGeoViewPortFactory<VP> ejbViewPort(){return new EjbGeoViewPortFactory<VP>(cViewPort);}
    public EjbGeoScaleFactory<SCALE> ejbScale() {return new EjbGeoScaleFactory<>(cScale);}
	public EjbGeoDataSourceFactory<L,D,DS> ejbDs() {return new EjbGeoDataSourceFactory<L,D,DS>(cL,cD,cDs);}

	
	public Comparator<SCALE> cmpScale(GeoScaleComparator.Type type)
	{
		GeoScaleComparator<L,D,SCALE> cF = new GeoScaleComparator<L,D,SCALE>();
		return cF.factory(type);
	}
}