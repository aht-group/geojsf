package org.geojsf.controller.processor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
		RULE result = null;
		for(RULE r : rules)
		{
			boolean isAboveLower = Objects.isNull(r.getLowerBound()) || r.getLowerBound()<value;
			boolean isBelowUpper = Objects.isNull(r.getUpperBound()) || value<=r.getUpperBound();
//			logger.info(r.toString()+" value:"+value);
			if(isAboveLower  && isBelowUpper) {result = r; break;}
		}
		if(Objects.isNull(result)) {logger.warn("NO RULE for "+value);}
//		logger.info("RESULT "+result.toString()+" value:"+value);
		return result;
	}
	
}