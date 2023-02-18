package org.geojsf.interfaces.model.domain.station;

import java.util.List;

import org.geojsf.interfaces.util.with.EjbWithGeometry;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

import com.vividsolutions.jts.geom.Point;

public interface GeoStation<L extends JeeslLang,
							D extends JeeslDescription,
							STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							TYPE extends JeeslStatus<L,D,TYPE>,
							SUBTYPE extends JeeslStatus<L,D,SUBTYPE>,
							SCHEME extends JeeslStatus<L,D,SCHEME>,
							CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							CAPT extends JeeslStatus<L,D,CAPT>,
							CAPS extends JeeslStatus<L,D,CAPS>>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>,EjbWithGeometry<Point>
{
	public enum Attributes{capabilities}
	
	TYPE getType();
	void setType(TYPE type);
	
	SUBTYPE getSubType();
	void setSubType(SUBTYPE subType);
	
	List<CAP> getCapabilities();
	void setCapabilities(List<CAP> capabilities);
	
	List<CODE> getCodes();
	void setCodes(List<CODE> codes);
}