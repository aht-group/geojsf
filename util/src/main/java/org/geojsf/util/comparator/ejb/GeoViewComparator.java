package org.geojsf.util.comparator.ejb;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.jeesl.controller.util.comparator.ejb.system.security.SecurityActionComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoViewComparator <VIEW extends GeoJsfView<?,?,VIEW>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityActionComparator.class);

    public static enum Type {legend};

    public GeoViewComparator()
    {
    	
    }
    
    public Comparator<VIEW> factory(Type type)
    {
        Comparator<VIEW> c = null;
        GeoViewComparator<VIEW> factory = new GeoViewComparator<>();
        switch (type)
        {
            case legend: c = factory.new LegendPositionComparator();break;
        }

        return c;
    }

    private class LegendPositionComparator implements Comparator<VIEW>
    {
        public int compare(VIEW a, VIEW b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getLegendNo(), b.getLegendNo());
			  return ctb.toComparison();
        }
    }
}