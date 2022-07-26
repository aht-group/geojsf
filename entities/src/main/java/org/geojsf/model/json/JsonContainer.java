package org.geojsf.model.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.model.json.location.JsonLocationArea1;
import org.geojsf.model.json.location.JsonLocationArea2;
import org.geojsf.model.json.location.JsonLocationArea3;
import org.geojsf.model.json.location.JsonLocationCountry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="container")
public class JsonContainer implements Serializable
{
	public static final long serialVersionUID=1;

	public JsonContainer() {}
	
	@JsonProperty("countries")
	private List<JsonLocationCountry> countries;
	public List<JsonLocationCountry> getCountries() {return countries;}
	public void setCountries(List<JsonLocationCountry> countries) {this.countries = countries;}
	
	@JsonProperty("areas1")
	private List<JsonLocationArea1> areas1;
	public List<JsonLocationArea1> getAreas1() {return areas1;}
	public void setAreas1(List<JsonLocationArea1> areas1) {this.areas1 = areas1;}
	
	@JsonProperty("areas2")
	private List<JsonLocationArea2> areas2;
	public List<JsonLocationArea2> getAreas2() {return areas2;}
	public void setAreas2(List<JsonLocationArea2> areas2) {this.areas2 = areas2;}
	
	@JsonProperty("areas3")
	private List<JsonLocationArea3> areas3;
	public List<JsonLocationArea3> getAreas3() {return areas3;}
	public void setAreas3(List<JsonLocationArea3> areas3) {this.areas3 = areas3;}
}