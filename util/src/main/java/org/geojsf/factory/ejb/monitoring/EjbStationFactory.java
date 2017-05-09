package org.geojsf.factory.ejb.monitoring;

import java.util.List;

import org.geojsf.factory.wkt.PointFactory;
import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;
import org.geojsf.model.xml.monitoring.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.io.ParseException;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbStationFactory<L extends UtilsLang,D extends UtilsDescription, STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>, TYPE extends UtilsStatus<TYPE,L,D>, SUBTYPE extends UtilsStatus<SUBTYPE,L,D>, SCHEME extends UtilsStatus<SCHEME,L,D>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>, CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStationFactory.class);
	
	final Class<STATION> cStation;

	private PointFactory gisPointFactory;
	
	private EjbLangFactory<L> efLang;
    private EjbDescriptionFactory<D> efDescription;
    
    public static <L extends UtilsLang,D extends UtilsDescription,
    				STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>,
    				TYPE extends UtilsStatus<TYPE,L,D>,
    				SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
    				SCHEME extends UtilsStatus<SCHEME,L,D>,
    				CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>,
    				CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
		EjbStationFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS> factory(final Class<L> cL,final Class<D> cD,final Class<STATION> cStation)
    {
        return new EjbStationFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>(cL,cD,cStation);
    }
    
    public EjbStationFactory(final Class<L> cL,final Class<D> cD, final Class<STATION> cStation)
    {
        this.cStation = cStation;
        
        gisPointFactory = new PointFactory();
        
        efLang = EjbLangFactory.createFactory(cL);
        efDescription = EjbDescriptionFactory.createFactory(cD);
    } 
	
	public STATION build(Station station,List<CAP> capabilities)
	{
		STATION ejb = null;
		try
		{
			ejb = cStation.newInstance();
			ejb.setCode(station.getCode());
			ejb.setName(efLang.getLangMap(station.getLangs()));
			ejb.setDescription(efDescription.create(station.getDescriptions()));
			ejb.setCapabilities(capabilities);
			
			if(station.isSetWkt() && station.getWkt().isSetValue())
			{
				ejb.setGeometry(gisPointFactory.build(station.getWkt()));
			}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		catch (ParseException e) {e.printStackTrace();}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		
        return ejb;
	}
	
	public STATION build(String[] langs)
	{
		STATION ejb = null;
		try
		{
			ejb = cStation.newInstance();
			ejb.setName(efLang.createEmpty(langs));
			ejb.setDescription(efDescription.createEmpty(langs));
			
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}		
        return ejb;
	}
}