package org.geojsf.exception;

public class GeoServerConfigurationException extends Exception
{
	private static final long serialVersionUID = 1;
	
	public GeoServerConfigurationException(){}
	
	public GeoServerConfigurationException(String msg)
	{
		super(msg);
	}
}
