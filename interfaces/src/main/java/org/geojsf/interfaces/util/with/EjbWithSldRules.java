package org.geojsf.interfaces.util.with;

import java.util.List;

import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithSldRules <RULE extends GeoJsfSldRule<?,?,?>> extends EjbWithId
{
	List<RULE> getRules();
	void setRules(List<RULE> rules);
}