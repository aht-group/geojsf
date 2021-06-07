package org.geojsf.factory.builder;

import org.geojsf.factory.ejb.monitoring.EjbStationFactory;
import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;
import org.geojsf.interfaces.model.obervation.station.GeoStationCode;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoMonitoringFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
										STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										TYPE extends JeeslStatus<L,D,TYPE>,
										SUBTYPE extends JeeslStatus<L,D,SUBTYPE>,
										SCHEME extends JeeslStatus<L,D,SCHEME>,
										CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAPT extends JeeslStatus<L,D,CAPT>,CAPS extends JeeslStatus<L,D,CAPS>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoMonitoringFactoryBuilder.class);
	
	private final Class<STATION> cStation; public Class<STATION> getClassStation() {return cStation;}

	public GeoMonitoringFactoryBuilder(final Class<L> cL, final Class<D> cD,
										final Class<STATION> cStation)
	{
		super(cL,cD);
		this.cStation=cStation;
	}
	
	public EjbStationFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS> station()
	{
		 return new EjbStationFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>(cL,cD,cStation);
    }
	
}