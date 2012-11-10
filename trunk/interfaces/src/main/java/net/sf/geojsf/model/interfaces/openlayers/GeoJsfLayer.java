package net.sf.geojsf.model.interfaces.openlayers;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface GeoJsfLayer<L extends UtilsLang,D extends UtilsDescription>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "securityViews";
	
}