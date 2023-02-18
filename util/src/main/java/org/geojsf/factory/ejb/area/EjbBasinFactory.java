package org.geojsf.factory.ejb.area;

import org.geojsf.factory.wkt.MultiPolygonFactory;
import org.geojsf.interfaces.model.domain.area.GeoBasin;
import org.geojsf.model.xml.area.Basin;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.io.ParseException;

public class EjbBasinFactory<L extends JeeslLang,D extends JeeslDescription,BASIN extends GeoBasin<L,D,BASIN,MODEL>,MODEL extends JeeslStatus<L,D,MODEL>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbBasinFactory.class);
	
	final Class<BASIN> cB;

	private MultiPolygonFactory gisMultiPolygonFactory;
	
	private EjbLangFactory<L> efLang;
    private EjbDescriptionFactory<D> efDescription;
    
    public static <L extends JeeslLang,D extends JeeslDescription,BASIN extends GeoBasin<L,D,BASIN,MODEL>,MODEL extends JeeslStatus<L,D,MODEL>>
		EjbBasinFactory<L,D,BASIN,MODEL> factory(final Class<L> cL,final Class<D> cD,final Class<BASIN> cB)
    {
        return new EjbBasinFactory<L,D,BASIN,MODEL>(cL,cD,cB);
    }
    
    public EjbBasinFactory(final Class<L> cL,final Class<D> cD, final Class<BASIN> cB)
    {
        this.cB = cB;
        
        gisMultiPolygonFactory = MultiPolygonFactory.instance();
        
        efLang = EjbLangFactory.instance(cL);
        efDescription = EjbDescriptionFactory.factory(cD);
    } 
	
	public BASIN build(Basin basin, MODEL model)
	{
		BASIN ejb = null;
		try
		{
			ejb = cB.newInstance();
			ejb.setSize(basin.getSize());
			ejb.setName(efLang.getLangMap(basin.getLangs()));
			ejb.setDescription(efDescription.create(basin.getDescriptions()));
			ejb.setModel(model);
			
			if(basin.isSetWkt() && basin.getWkt().isSetValue())
			{
				ejb.setGeometry(gisMultiPolygonFactory.build(basin.getWkt()));
			}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		catch (ParseException e) {e.printStackTrace();}
		catch (JeeslConstraintViolationException e) {e.printStackTrace();}
		
        return ejb;
	}
}