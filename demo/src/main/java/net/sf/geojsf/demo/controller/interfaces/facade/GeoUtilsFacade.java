package net.sf.geojsf.demo.controller.interfaces.facade;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.controller.interfaces.UtilsTrackerFacade;
import net.sf.geojsf.demo.model.user.GeoUser;

public interface GeoUtilsFacade extends GeoFacade,UtilsSecurityFacade,UtilsTrackerFacade
{
	void viewTacker(GeoUser user, String code);
	void viewTacker(String code);
}