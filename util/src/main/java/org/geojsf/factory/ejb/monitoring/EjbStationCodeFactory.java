package org.geojsf.factory.ejb.monitoring;

import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;
import org.geojsf.interfaces.model.obervation.station.GeoStationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbStationCodeFactory<L extends UtilsLang, D extends UtilsDescription,
									STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
									TYPE extends UtilsStatus<TYPE,L,D>, SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
									SCHEME extends UtilsStatus<SCHEME,L,D>,
									CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
									CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
									CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStationCodeFactory.class);
	
	private final Class<CODE> cCode;
    
    public static <L extends UtilsLang, D extends UtilsDescription,
    				STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				TYPE extends UtilsStatus<TYPE,L,D>,
    				SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
    				SCHEME extends UtilsStatus<SCHEME,L,D>,
    				CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
		EjbStationCodeFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS> factory(final Class<CODE> cCode)
    {
        return new EjbStationCodeFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>(cCode);
    }
    
    public EjbStationCodeFactory(final Class<CODE> cCode)
    {
        this.cCode = cCode;
    } 
	
	public CODE build(STATION station)
	{
		CODE ejb = null;
		try
		{
			ejb = cCode.newInstance();
			ejb.setStation(station);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
        return ejb;
	}
}