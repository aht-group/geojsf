package org.geojsf.factory.ejb.monitoring;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.monitoring.GeoStation;
import org.geojsf.model.xml.monitoring.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class EjbStationFactory<L extends UtilsLang,D extends UtilsDescription,CAP extends UtilsStatus<CAP,L,D>,STATION extends GeoStation<L,D,CAP>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStationFactory.class);
	
	final Class<STATION> cStation;
	
	private WKTReader wktReader;
	
	private EjbLangFactory<L> efLang;
    private EjbDescriptionFactory<D> efDescription;
    
    public static <L extends UtilsLang,D extends UtilsDescription,CAP extends UtilsStatus<CAP,L,D>,STATION extends GeoStation<L,D,CAP>>
		EjbStationFactory<L,D,CAP,STATION> factory(final Class<L> cL,final Class<D> cD,final Class<STATION> cStation)
    {
        return new EjbStationFactory<L,D,CAP,STATION>(cL,cD,cStation);
    }
    
    public EjbStationFactory(final Class<L> cL,final Class<D> cD, final Class<STATION> cStation)
    {
        this.cStation = cStation;
        
        wktReader = new WKTReader();
        
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
				ejb.setGeometry((Point)wktReader.read(station.getWkt().getValue()));
			}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		catch (ParseException e) {e.printStackTrace();}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		
        return ejb;
	}
}