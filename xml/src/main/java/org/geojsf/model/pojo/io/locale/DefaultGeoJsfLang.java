package org.geojsf.model.pojo.io.locale;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

public class DefaultGeoJsfLang implements JeeslLang,EjbRemoveable,Serializable,EjbPersistable
{
	public static final long serialVersionUID=1;
	

	public static String[] defaultLangs = {"fr","en","de"};
	
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	private String lkey;
	public String getLkey() {return lkey;}
	public void setLkey(String lkey) {this.lkey = lkey;}
	
	private String lang;
	public String getLang() {return lang;}
	public void setLang(String name) {this.lang = name;}

	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" ["+lkey+"]");
			sb.append(" "+lang);
		return sb.toString();
	}
}