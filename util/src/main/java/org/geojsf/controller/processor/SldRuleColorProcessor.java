package org.geojsf.controller.processor;

import java.io.Serializable;
import java.util.List;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SldRuleColorProcessor <SLD extends GeoJsfSld<?,?,?,?,RULE,?,?>,
									RULE extends GeoJsfSldRule<?,?,?>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(SldRuleColorProcessor.class);
	
	private final List<RULE> rules;
	
	public SldRuleColorProcessor(SLD sld)
	{
		rules = sld.getRules();
	}
	
	public RULE toRule(double value)
	{
		for(RULE r : rules)
		{
			if(r.getLowerBound()<value && value<=r.getUpperBound()) {return r;}
		}
		return null;
	}
	
}