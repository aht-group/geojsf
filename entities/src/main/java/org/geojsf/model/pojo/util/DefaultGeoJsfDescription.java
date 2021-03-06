package org.geojsf.model.pojo.util;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;

public class DefaultGeoJsfDescription implements JeeslDescription,Serializable,EjbPersistable
{
	public static final long serialVersionUID=1;
	
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	private String lkey;
	@Override public String getLkey() {return lkey;}
	@Override public void setLkey(String lkey) {this.lkey = lkey;}
	
	private String lang;
	@Override public String getLang() {return lang;}
	@Override public void setLang(String name) {this.lang = name;}
	
	private Boolean styled;
	@Override public Boolean getStyled() {return styled;}
	@Override public void setStyled(Boolean styled) {this.styled = styled;}

	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" ["+lkey+"]");
			sb.append(" "+lang);
		return sb.toString();
	}
}