package org.geojsf.util.comparator;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.symbol.UtilsGraphic;
import net.sf.ahtutils.util.comparator.ejb.security.SecurityActionComparator;

public class GeoViewComparator <L extends UtilsLang,
								D extends UtilsDescription,
								G extends UtilsGraphic<L,D,GT,GS>,
								GT extends UtilsStatus<GT,L,D>,
								GS extends UtilsStatus<GS,L,D>,
								CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
								RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityActionComparator.class);

    public static enum Type {legend};

    public GeoViewComparator()
    {
    	
    }
    
    public Comparator<VIEW> factory(Type type)
    {
        Comparator<VIEW> c = null;
        GeoViewComparator<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE> factory = new GeoViewComparator<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>();
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