package org.geojsf.interfaces.model.sld;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSldRule<L extends UtilsLang,
						   D extends UtilsDescription,
						   G extends JeeslGraphic<L,D,G,GT,GS>,
						   GT extends UtilsStatus<GT,L,D>,
						   GS extends UtilsStatus<GS,L,D>,
						   TEMPLATE extends GeoJsfSldTemplate<L,D,TEMPLATE,TYPE>,
						   TYPE extends UtilsStatus<TYPE,L,D>,
						   SLD extends GeoJsfSld<L,D,G,GT,GS,TEMPLATE,TYPE,SLD,RULE>,
						   RULE extends GeoJsfSldRule<L,D,G,GT,GS,TEMPLATE,TYPE,SLD,RULE>>
			extends EjbWithId,EjbWithPosition,EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Type{symbol,interval}
		
	Double getLowerBound();
	void setLowerBound(Double lowerBound);
	
	Double getUpperBound();
	void setUpperBound(Double upperBound);
	
	G getGraphic();
	void setGraphic(G graphic);
}