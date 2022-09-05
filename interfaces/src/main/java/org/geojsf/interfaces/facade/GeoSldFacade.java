package org.geojsf.interfaces.facade;

import java.util.List;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.with.EjbWithSldRules;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface GeoSldFacade <L extends JeeslLang, D extends JeeslDescription,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
								SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
								RULE extends GeoJsfSldRule<L,D,?>
								>
					extends JeeslFacade
{
	SLD load(SLD sld);
	RULE load(Class<RULE> cRule, RULE rule);

	List<SLD> fLibrarySlds();
	
	<W extends EjbWithSldRules<RULE>> RULE save(Class<W> cW, W entity, RULE rule) throws JeeslLockingException, JeeslConstraintViolationException;
	<W extends EjbWithSldRules<RULE>> void delete(Class<W> cW, W entity, RULE rule) throws JeeslLockingException, JeeslConstraintViolationException;
}