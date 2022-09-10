package org.geojsf.util.nominatim;

import com.vividsolutions.jts.geom.Point;
import javax.xml.bind.JAXBException;

public class TestNominatim {
    
    public static void main(String[] args) throws JAXBException
    {
		String location	    = "Essen,Baumstr";
		String coordinates  = GeoCodingUtil.getCoordinates(location);
		System.out.println(location + " has the coordinates " +coordinates);
		Point  point	    = GeoCodingUtil.getPoint(location);
		System.out.println(location + " has the point coordinates " +point.getY() + ", " +point.getX());
		String reverse		= GeoCodingUtil.getPlaceInfoString(point.getY(), point.getX());
		System.out.println(reverse);
    }
}
