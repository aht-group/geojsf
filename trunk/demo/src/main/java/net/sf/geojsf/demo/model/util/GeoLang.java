package net.sf.geojsf.demo.model.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
//@Audited
@Table(name = "Lang")
@EjbErNode(name="Lang")

public class GeoLang implements UtilsLang,EjbRemoveable,Serializable,EjbPersistable
{
	public static final long serialVersionUID=1;
	
	public static final String[] keys = {"de","en","fr"};
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String lkey;
	
	@NotNull
	private String lang;
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getLkey() {return lkey;}
	public void setLkey(String lkey) {this.lkey = lkey;}
	
	public String getLang() {return lang;}
	public void setLang(String name) {this.lang = name;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" ["+lkey+"]");
			sb.append(" "+lang);
		return sb.toString();
	}
/*	
	
	
	public synchronized static Map<String,AhtLang> createMap(AhtLang... languages)
	{
		Map<String,AhtLang> langMap = new Hashtable<String, AhtLang>();
		for(AhtLang lang : languages)
		{
			langMap.put(lang.getLkey(), lang);
		}
		return langMap;
	}
	
	public synchronized static AhtLang create(String key, String lang)
	{
		AhtLang pl = new AhtLang();
		pl.setLkey(key);
		pl.setLang(lang);
		return pl;
	}
	*/
}