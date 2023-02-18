package org.geojsf.factory.ejb.monitoring;

import org.geojsf.interfaces.model.domain.monitoring.GeoBushFire;
import org.geojsf.model.xml.monitoring.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class EjbBushFireFactory<B extends GeoBushFire>
{
	final static Logger logger = LoggerFactory.getLogger(EjbBushFireFactory.class);
	
	final Class<B> clBushFire;
	private WKTReader wktReader;
	
    public EjbBushFireFactory(final Class<B> clBushFire)
    {
        this.clBushFire = clBushFire;
        wktReader = new WKTReader();
    } 
    
    public static <B extends GeoBushFire> EjbBushFireFactory<B> factory(final Class<B> clBushFire)
    {
        return new EjbBushFireFactory<B>(clBushFire);
    }
    
    public B build(Data data)
	{
    	B ejb;
		try
		{
			ejb = clBushFire.newInstance();
			ejb.setRecord(data.getRecord().toGregorianCalendar().getTime());
			
			ejb.setGeometry((Point)wktReader.read(data.getWkt().getValue()));
		}
		catch (InstantiationException e) {throw new RuntimeException(e);}
		catch (IllegalAccessException e) {throw new RuntimeException(e);}
		catch (ParseException e) {throw new RuntimeException(e);}

		return ejb;
	}
}