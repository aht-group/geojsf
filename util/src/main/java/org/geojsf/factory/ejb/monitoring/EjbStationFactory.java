package org.geojsf.factory.ejb.monitoring;

import java.util.List;
import java.util.Objects;

import org.geojsf.factory.wkt.PointFactory;
import org.geojsf.interfaces.model.domain.station.GeoStation;
import org.geojsf.interfaces.model.domain.station.GeoStationCapability;
import org.geojsf.interfaces.model.domain.station.GeoStationCode;
import org.geojsf.model.xml.monitoring.Station;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.io.ParseException;

public class EjbStationFactory<L extends JeeslLang,D extends JeeslDescription, STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, TYPE extends JeeslStatus<L,D,TYPE>, SUBTYPE extends JeeslStatus<L,D,SUBTYPE>, SCHEME extends JeeslStatus<L,D,SCHEME>, CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAPT extends JeeslStatus<L,D,CAPT>,CAPS extends JeeslStatus<L,D,CAPS>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStationFactory.class);
	
	final Class<STATION> cStation;

	private PointFactory gisPointFactory;
	
	private EjbLangFactory<L> efLang;
    private EjbDescriptionFactory<D> efDescription;
    
    private static <L extends JeeslLang,D extends JeeslDescription,
    				STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				TYPE extends JeeslStatus<L,D,TYPE>,
    				SUBTYPE extends JeeslStatus<L,D,SUBTYPE>,
    				SCHEME extends JeeslStatus<L,D,SCHEME>,
    				CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
    				CAPT extends JeeslStatus<L,D,CAPT>,CAPS extends JeeslStatus<L,D,CAPS>>
		EjbStationFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS> factory(final Class<L> cL,final Class<D> cD,final Class<STATION> cStation)
    {
        return new EjbStationFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>(cL,cD,cStation);
    }
    
    public EjbStationFactory(final Class<L> cL,final Class<D> cD, final Class<STATION> cStation)
    {
        this.cStation = cStation;
        
        gisPointFactory = new PointFactory();
        
        efLang = EjbLangFactory.instance(cL);
        efDescription = EjbDescriptionFactory.factory(cD);
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
			
			if(station.isSetWkt() && Objects.nonNull(station.getWkt().getValue()))
			{
				ejb.setGeometry(gisPointFactory.build(station.getWkt()));
			}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		catch (ParseException e) {e.printStackTrace();}
		catch (JeeslConstraintViolationException e) {e.printStackTrace();}
		
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