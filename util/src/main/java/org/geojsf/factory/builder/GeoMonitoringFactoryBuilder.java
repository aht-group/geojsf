package org.geojsf.factory.builder;

import org.geojsf.factory.ejb.monitoring.EjbStationFactory;
import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;
import org.geojsf.interfaces.model.obervation.station.GeoStationCode;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class GeoMonitoringFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
										STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
										SCHEME extends UtilsStatus<SCHEME,L,D>,
										CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
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