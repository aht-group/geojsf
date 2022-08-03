package org.geojsf.model.json.location;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="country")
public class JsonLocationCountry implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id=id;}

	@JsonProperty("code")
	private String code;
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	@JsonProperty("label")
	private String label;
	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}
	
	@JsonProperty("area1")
	private List<JsonLocationArea1> area1;
	public List<JsonLocationArea1> getArea1() {return area1;}
	public void setArea1(List<JsonLocationArea1> area1) {this.area1 = area1;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}