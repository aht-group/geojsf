package org.geojsf.demo.controller.interfaces.facade;

import org.geojsf.demo.model.user.GeoUser;

import net.sf.ahtutils.controller.interfaces.UtilsSecurityFacade;
import net.sf.ahtutils.controller.interfaces.UtilsTrackerFacade;

public interface GeoUtilsFacade extends GeoFacade,UtilsSecurityFacade,UtilsTrackerFacade
{
	void viewTacker(GeoUser user, String code);
	void viewTacker(String code);
}