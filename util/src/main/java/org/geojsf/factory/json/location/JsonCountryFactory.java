package org.geojsf.factory.json.location;

import java.io.Serializable;

import org.geojsf.model.json.location.JsonLocationCountry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonCountryFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(JsonCountryFactory.class);
	public static final long serialVersionUID=1;
	
	public static JsonLocationCountry label(String value)
	{
		JsonLocationCountry json = new JsonLocationCountry();
		json.setLabel(value);
		return json;
	}
}