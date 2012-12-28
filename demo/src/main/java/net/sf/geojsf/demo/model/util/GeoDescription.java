package net.sf.geojsf.demo.model.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(name = "Description")
@EjbErNode(name="Description")
public class GeoDescription implements UtilsDescription,Serializable,EjbPersistable
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String lkey;
	
	@NotNull
	@Lob
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
	public Lang toJaxb()
	{
		Lang jaxbLang = new Lang();
		jaxbLang.setLang(lkey);
		jaxbLang.setName(lang);
		return jaxbLang;
	}
	
	public synchronized static Map<String,AhtDescription> createMap(AhtDescription... languages)
	{
		Map<String,AhtDescription> langMap = new Hashtable<String, AhtDescription>();
		for(AhtDescription lang : languages)
		{
			langMap.put(lang.getLkey(), lang);
		}
		return langMap;
	}
	
	public synchronized static AhtDescription create(String key, String lang)
	{
		AhtDescription pl = new AhtDescription();
		pl.setLkey(key);
		pl.setLang(lang);
		return pl;
	}
	
	public static Map<String,AhtDescription> getLangMap(List<Lang> langList)
	{
		Map<String,AhtDescription> mapLangs = new Hashtable<String,AhtDescription>();
		for(Lang jaxbLang : langList)
		{
			AhtDescription pisLang = new AhtDescription();
			pisLang.setLkey(jaxbLang.getLang());
			pisLang.setLang(jaxbLang.getName());
			mapLangs.put(pisLang.getLkey(), pisLang);
		}
		return mapLangs;
	}
	
	public static List<Lang> toJaxb(Map<String, AhtDescription> mLang)
	{
		List<Lang> lResult = new ArrayList<Lang>();
		for(String key : mLang.keySet())
		{
			lResult.add(mLang.get(key).toJaxb());
		}
		return lResult;
	}
	*/
}