package org.geojsf.util.comparator.ejb;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.jeesl.controller.util.comparator.ejb.system.security.SecurityActionComparator;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoScaleComparator <L extends JeeslLang,D extends JeeslDescription,	
									SCALE extends GeoJsfScale<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityActionComparator.class);

    public static enum Type {value};

    public GeoScaleComparator()
    {
    	
    }
    
    public Comparator<SCALE> factory(Type type)
    {
        Comparator<SCALE> c = null;
        GeoScaleComparator<L,D,SCALE> factory = new GeoScaleComparator<L,D,SCALE>();
        switch (type)
        {
            case value: c = factory.new LegendPositionComparator();break;
        }

        return c;
    }

    private class LegendPositionComparator implements Comparator<SCALE>
    {
        public int compare(SCALE a, SCALE b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getValue(), b.getValue());
			  return ctb.toComparison();
        }
    }
}