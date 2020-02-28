package org.geojsf.interfaces.model.sld;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface GeoJsfSldRule<L extends JeeslLang, D extends JeeslDescription,
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