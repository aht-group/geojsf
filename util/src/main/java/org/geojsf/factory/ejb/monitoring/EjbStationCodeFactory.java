package org.geojsf.factory.ejb.monitoring;

import org.geojsf.interfaces.model.domain.station.GeoStation;
import org.geojsf.interfaces.model.domain.station.GeoStationCapability;
import org.geojsf.interfaces.model.domain.station.GeoStationCode;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbStationCodeFactory<L extends JeeslLang, D extends JeeslDescription,
									STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
									TYPE extends JeeslStatus<L,D,TYPE>, SUBTYPE extends JeeslStatus<L,D,SUBTYPE>,
									SCHEME extends JeeslStatus<L,D,SCHEME>,
									CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
									CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
									CAPT extends JeeslStatus<L,D,CAPT>,CAPS extends JeeslStatus<L,D,CAPS>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStationCodeFactory.class);
	
	private final Class<CODE> cCode;
    
    public static <L extends JeeslLang, D extends JeeslDescription,
    				STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				TYPE extends JeeslStatus<L,D,TYPE>,
    				SUBTYPE extends JeeslStatus<L,D,SUBTYPE>,
    				SCHEME extends JeeslStatus<L,D,SCHEME>,
    				CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				CAPT extends JeeslStatus<L,D,CAPT>,CAPS extends JeeslStatus<L,D,CAPS>>
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