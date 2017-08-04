package org.geojsf.interfaces.model.obervation.station;

import java.util.List;

import org.geojsf.interfaces.model.with.EjbWithGeometry;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

import com.vividsolutions.jts.geom.Point;

public interface GeoStation<L extends UtilsLang,
							D extends UtilsDescription,
							STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
							SCHEME extends UtilsStatus<SCHEME,L,D>,
							CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
							CAPT extends UtilsStatus<CAPT,L,D>,
							CAPS extends UtilsStatus<CAPS,L,D>>
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