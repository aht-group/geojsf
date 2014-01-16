package org.geojsf.test.model;

import java.io.Serializable;

import javax.persistence.Column;

import org.geojsf.interfaces.model.EjbWithGeometry;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

public class SampleSpatialEntity implements Serializable,EjbWithGeometry<Point>
{
	private static final long serialVersionUID = 1L;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	private long id;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Type(type = "org.hibernate.spatial.GeometryType")
	@Column(name="the_geom", columnDefinition="Geometry")
	private Point geometry;
	@Override public Point getGeometry() {return geometry;}
	@Override public void setGeometry(Point geometry) {this.geometry = geometry;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" ");
		return sb.toString();
	}
}