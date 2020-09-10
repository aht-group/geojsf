package org.geojsf.util.nominatim;

import com.vividsolutions.jts.geom.Point;
import javax.xml.bind.JAXBException;

public class TestNominatim {
    
    public static void main(String[] args) throws JAXBException
    {
	// Other example: Baumstr+Essen&format=xml&polygon=1&addressdetails=1
	String location	    = "Baumstr. Essen";
	String coordinates  = GeoCodingUtil.getCoordinates(location);
	System.out.println(location + " has the coordinates " +coordinates);
	Point  point	    = GeoCodingUtil.getPoint(location);
	System.out.println(location + " has the point coordinates " +point.getY() + ", " +point.getX());
    }
}
