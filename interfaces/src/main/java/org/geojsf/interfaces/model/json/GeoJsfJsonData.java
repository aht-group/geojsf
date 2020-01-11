package org.geojsf.interfaces.model.json;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface GeoJsfJsonData<L extends UtilsLang, D extends UtilsDescription,
						   JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
						   JL extends GeoJsfLocationLevel<JL,L,D,?>
						   >
			extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable
{
	public enum Attributes{quality,level}
	
	JQ getQuality();
	void setQuality(JQ quality);
	
	JL getLevel();
	void setLevel(JL level);
	
	String getUrl();
	void setUrl(String url);
	
	String getJoinCode();
	void setJoinCode(String joinCode);
}