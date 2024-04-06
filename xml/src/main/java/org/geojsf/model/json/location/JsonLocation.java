package org.geojsf.model.json.location;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="location")
public class JsonLocation implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("country")
	private JsonLocationCountry country;
	public JsonLocationCountry getCountry() {return country;}
	public void setCountry(JsonLocationCountry country) {this.country = country;}
	
	@JsonProperty("area1")
	private JsonLocationArea1 area1;
	public JsonLocationArea1 getArea1() {return area1;}
	public void setArea1(JsonLocationArea1 area1) {this.area1 = area1;}


	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}