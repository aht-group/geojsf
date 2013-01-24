package org.geojsf.demo.controller.factory.ejb.util.tracker;

import net.sf.ahtutils.controller.factory.ejb.tracker.EjbEventTrackerFactory;
import net.sf.geojsf.demo.model.user.GeoUser;
import net.sf.geojsf.demo.model.util.GeoDescription;
import net.sf.geojsf.demo.model.util.GeoLang;
import net.sf.geojsf.demo.model.util.security.SecurityAction;
import net.sf.geojsf.demo.model.util.security.SecurityCategory;
import net.sf.geojsf.demo.model.util.security.SecurityRole;
import net.sf.geojsf.demo.model.util.security.SecurityUsecase;
import net.sf.geojsf.demo.model.util.security.SecurityView;
import net.sf.geojsf.demo.model.util.tracker.GeoViewTracker;

public class EjbViewTrackerFactory
{	
	private static EjbEventTrackerFactory<GeoLang,GeoDescription,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,GeoUser,GeoViewTracker,SecurityView> f;
	
	private static void factory()
	{
		if(f==null)
		{
			f = EjbEventTrackerFactory.createFactory(GeoViewTracker.class);
		}
	}
	
	public static GeoViewTracker build(SecurityView view, GeoUser user)
	{
		factory();
		GeoViewTracker tracker = f.create(view);
		if(user!=null && user.getId()>0){tracker.setUser(user);}
		return tracker;
	}
	
}