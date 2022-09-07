package org.geojsf.interfaces.model.json;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslAttributes;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslDescription;

@DownloadJeeslDescription
@DownloadJeeslAttributes
public interface GeoJsfJsonData<L extends JeeslLang, D extends JeeslDescription,
						   JQ extends GeoJsfJsonQuality<JQ,L,D,?>,
						   JL extends GeoJsfLocationLevel<L,D,JL,?>
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