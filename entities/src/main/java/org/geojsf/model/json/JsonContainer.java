package org.geojsf.model.json;

import java.io.Serializable;
import java.util.List;

import org.geojsf.model.json.location.JsonLocationArea1;
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
}