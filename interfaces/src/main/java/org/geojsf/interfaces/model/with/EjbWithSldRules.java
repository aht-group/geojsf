package org.geojsf.interfaces.model.with;

import java.util.List;

import org.geojsf.interfaces.model.sld.GeoJsfSldRule;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithSldRules <RULE extends GeoJsfSldRule<?,?,?>> extends EjbWithId
{
	List<RULE> getRules();
	void setRules(List<RULE> rules);
}