package org.geojsf.factory.builder;

import org.geojsf.factory.ejb.meta.EjbGeoJsonDataFactory;
import org.geojsf.interfaces.model.json.GeoJsfJsonData;
import org.geojsf.interfaces.model.json.GeoJsfJsonQuality;
import org.geojsf.interfaces.model.json.GeoJsfLocationLevel;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsonFactoryBuilder<L extends JeeslLang, D extends JeeslDescription,
									JSON extends GeoJsfJsonData<L,D,JQ,JL>,
									JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
									JL extends GeoJsfLocationLevel<L,D,JL,?>
										>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsonFactoryBuilder.class);
	
	private final Class<JSON> cJson; public Class<JSON> getClassJson() {return cJson;}
	private final Class<JQ> cJsonQuality; public Class<JQ> getClassQuality() {return cJsonQuality;}
	private final Class<JL> cLocationLevel; public Class<JL> getClassLevel() {return cLocationLevel;}

	public GeoJsonFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<JSON> cJson,
								final Class<JQ> cJsonQuality,
								final Class<JL> cLocationLevel)
	{
		super(cL,cD);
		this.cJson=cJson;
		this.cJsonQuality=cJsonQuality;
		this.cLocationLevel=cLocationLevel;
	}
	
	public EjbGeoJsonDataFactory<JSON> ejbJson() {return new EjbGeoJsonDataFactory<JSON>(cJson);}
}