package org.geojsf.factory.ejb.core;

import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoMapFactory<L extends JeeslLang,D extends JeeslDescription,
								MAP extends GeoJsfMap<L,D,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoMapFactory.class);
	
	final Class<MAP> cMap;
	private EjbLangFactory<L> fLang;
    
    public EjbGeoMapFactory(final Class<L> cLang,final Class<MAP> cMap)
    {
        this.cMap = cMap;
        fLang = EjbLangFactory.factory(cLang);
    } 
	
	public MAP create(String code,  String[] langKeys)
	{
		MAP ejb = build();
		ejb.setName(fLang.createEmpty(langKeys));
		ejb.setCode(code);
        return ejb;
    }
	
	public MAP build()
	{
		MAP ejb = null;
		try
		{
			ejb = cMap.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
        return ejb;
    }
}