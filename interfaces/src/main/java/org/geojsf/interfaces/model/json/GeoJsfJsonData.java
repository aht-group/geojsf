package org.geojsf.interfaces.model.json;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;

public interface GeoJsfJsonData<L extends JeeslLang, D extends JeeslDescription,
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