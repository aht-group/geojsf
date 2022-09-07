package org.geojsf.factory.builder;

import org.geojsf.factory.ejb.meta.EjbGeoDataSourceFactory;
import org.geojsf.factory.ejb.meta.EjbGeoJsonDataFactory;
import org.geojsf.factory.ejb.meta.EjbGeoViewPortFactory;
import org.geojsf.interfaces.model.json.GeoJsfJsonData;
import org.geojsf.interfaces.model.json.GeoJsfJsonQuality;
import org.geojsf.interfaces.model.json.GeoJsfLocationLevel;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoMetaFactoryBuilder<L extends JeeslLang, D extends JeeslDescription,
									DS extends GeoJsfDataSource<L,D,?>,
									VP extends GeoJsfViewPort,
									JSON extends GeoJsfJsonData<L,D,JQ,JL>,
									JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
									JL extends GeoJsfLocationLevel<L,D,JL,?>
										>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoMetaFactoryBuilder.class);
	
	private final Class<DS> cDs; public Class<DS> getClassDs() {return cDs;}
	private final Class<VP> cViewPort; public Class<VP> getcViewPort() {return cViewPort;}
	private final Class<JSON> cJson; public Class<JSON> getClassJson() {return cJson;}
	private final Class<JQ> cJsonQuality; public Class<JQ> getClassQuality() {return cJsonQuality;}
	private final Class<JL> cLocationLevel; public Class<JL> getClassLevel() {return cLocationLevel;}

	public GeoMetaFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<DS> cDs,
								final Class<VP> cViewPort,
								final Class<JSON> cJson,
								final Class<JQ> cJsonQuality,
								final Class<JL> cLocationLevel)
	{
		super(cL,cD);
		this.cDs=cDs;
		
		this.cViewPort=cViewPort;
		
		this.cJson=cJson;
		this.cJsonQuality=cJsonQuality;
		this.cLocationLevel=cLocationLevel;
	}
	
    public EjbGeoViewPortFactory<VP> ejbViewPort(){return new EjbGeoViewPortFactory<VP>(cViewPort);}
	public EjbGeoDataSourceFactory<L,D,DS> ejbDs() {return new EjbGeoDataSourceFactory<L,D,DS>(cL,cD,cDs);}
	public EjbGeoJsonDataFactory<JSON> ejbJson() {return new EjbGeoJsonDataFactory<JSON>(cJson);}
}