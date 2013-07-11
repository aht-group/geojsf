package org.geojsf.util.wfs;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SampleSpatialEntity implements Serializable,EjbWithId
{
	private static final long serialVersionUID = 1L;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	private long id;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" ");
		return sb.toString();
	}
}