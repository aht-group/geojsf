package org.geojsf.factory.ejb.monitoring;

import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;
import org.geojsf.interfaces.model.obervation.station.GeoStationCode;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbCapabilityFactory<L extends JeeslLang,D extends JeeslDescription, STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, TYPE extends JeeslStatus<TYPE,L,D>, SUBTYPE extends JeeslStatus<SUBTYPE,L,D>, SCHEME extends JeeslStatus<SCHEME,L,D>, CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAPT extends JeeslStatus<CAPT,L,D>,CAPS extends JeeslStatus<CAPS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbCapabilityFactory.class);
	
	final Class<CAP> cCapability;
    
    public static <L extends JeeslLang,D extends JeeslDescription,
    				STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				TYPE extends JeeslStatus<TYPE,L,D>,
    				SUBTYPE extends JeeslStatus<SUBTYPE,L,D>,
    				SCHEME extends JeeslStatus<SCHEME,L,D>,
    				CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				CAPT extends JeeslStatus<CAPT,L,D>,
    				CAPS extends JeeslStatus<CAPS,L,D>>
		EjbCapabilityFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS> factory(final Class<CAP> cCapability)
    {
        return new EjbCapabilityFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>(cCapability);
    }
    
    public EjbCapabilityFactory(final Class<CAP> cCapability)
    {
        this.cCapability = cCapability;
    } 
	
	public CAP build(STATION station, String code, CAPT type, CAPS status)
	{
		CAP ejb = null;
		try
		{
			ejb = cCapability.newInstance();
			ejb.setStation(station);
			ejb.setCode(code);
			ejb.setType(type);
			ejb.setStatus(status);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
        return ejb;
	}
}