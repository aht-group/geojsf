package org.geojsf.interfaces.model.obervation.station;

import java.util.List;

import org.geojsf.interfaces.model.with.EjbWithGeometry;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

import com.vividsolutions.jts.geom.Point;

public interface GeoStation<L extends JeeslLang,
							D extends JeeslDescription,
							STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							TYPE extends JeeslStatus<TYPE,L,D>,
							SUBTYPE extends JeeslStatus<SUBTYPE,L,D>,
							SCHEME extends JeeslStatus<SCHEME,L,D>,
							CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							CAPT extends JeeslStatus<CAPT,L,D>,
							CAPS extends JeeslStatus<CAPS,L,D>>
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