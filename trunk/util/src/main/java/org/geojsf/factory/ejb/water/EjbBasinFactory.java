package org.geojsf.factory.ejb.water;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.factory.wkt.MultiPolygonFactory;
import org.geojsf.interfaces.model.water.GeoBasin;
import org.geojsf.model.xml.water.Basin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.io.ParseException;

public class EjbBasinFactory<L extends UtilsLang,D extends UtilsDescription,BASIN extends GeoBasin<L,D,BASIN,MODEL>,MODEL extends UtilsStatus<MODEL,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbBasinFactory.class);
	
	final Class<BASIN> cB;

	private MultiPolygonFactory gisMultiPolygonFactory;
	
	private EjbLangFactory<L> efLang;
    private EjbDescriptionFactory<D> efDescription;
    
    public static <L extends UtilsLang,D extends UtilsDescription,BASIN extends GeoBasin<L,D,BASIN,MODEL>,MODEL extends UtilsStatus<MODEL,L,D>>
		EjbBasinFactory<L,D,BASIN,MODEL> factory(final Class<L> cL,final Class<D> cD,final Class<BASIN> cB)
    {
        return new EjbBasinFactory<L,D,BASIN,MODEL>(cL,cD,cB);
    }
    
    public EjbBasinFactory(final Class<L> cL,final Class<D> cD, final Class<BASIN> cB)
    {
        this.cB = cB;
        
        gisMultiPolygonFactory = new MultiPolygonFactory();
        
        efLang = EjbLangFactory.createFactory(cL);
        efDescription = EjbDescriptionFactory.createFactory(cD);
    } 
	
	public BASIN build(Basin basin, MODEL model)
	{
		BASIN ejb = null;
		try
		{
			ejb = cB.newInstance();
			ejb.setArea(basin.getArea());
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
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		
        return ejb;
	}
}