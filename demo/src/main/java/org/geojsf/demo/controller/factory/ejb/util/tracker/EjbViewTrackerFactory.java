package org.geojsf.demo.controller.factory.ejb.util.tracker;

import org.geojsf.demo.model.user.GeoUser;
import org.geojsf.demo.model.util.GeoDescription;
import org.geojsf.demo.model.util.GeoLang;
import org.geojsf.demo.model.util.security.SecurityAction;
import org.geojsf.demo.model.util.security.SecurityCategory;
import org.geojsf.demo.model.util.security.SecurityRole;
import org.geojsf.demo.model.util.security.SecurityUsecase;
import org.geojsf.demo.model.util.security.SecurityView;
import org.geojsf.demo.model.util.tracker.GeoViewTracker;

import net.sf.ahtutils.controller.factory.ejb.tracker.EjbEventTrackerFactory;

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