package org.geojsf.factory.ejb.monitoring;

import org.geojsf.interfaces.model.monitoring.GeoStation;
import org.geojsf.interfaces.model.monitoring.GeoStationCapability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbCapabilityFactory<L extends UtilsLang,D extends UtilsDescription, STATION extends GeoStation<L,D,STATION,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,CAP,CAPT,CAPS>, CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbCapabilityFactory.class);
	
	final Class<CAP> cCapability;
    
    public static <L extends UtilsLang,D extends UtilsDescription, STATION extends GeoStation<L,D,STATION,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,CAP,CAPT,CAPS>, CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
		EjbCapabilityFactory<L,D,STATION,CAP,CAPT,CAPS> factory(final Class<CAP> cCapability)
    {
        return new EjbCapabilityFactory<L,D,STATION,CAP,CAPT,CAPS>(cCapability);
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