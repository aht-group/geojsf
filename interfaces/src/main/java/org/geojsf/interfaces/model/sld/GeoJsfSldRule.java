package org.geojsf.interfaces.model.sld;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSldRule<L extends UtilsLang, D extends UtilsDescription,
							G extends JeeslGraphic<L,D,?,?,?>>
			extends Serializable,EjbWithId,EjbWithPosition,EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Type{symbol,interval}
		
	Double getLowerBound();
	void setLowerBound(Double lowerBound);
	
	Double getUpperBound();
	void setUpperBound(Double upperBound);
	
	G getGraphic();
	void setGraphic(G graphic);
}