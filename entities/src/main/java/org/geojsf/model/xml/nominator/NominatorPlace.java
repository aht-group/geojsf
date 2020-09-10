package org.geojsf.model.xml.nominator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "place")
public class NominatorPlace {
	
    @XmlAttribute
    protected String lon;

    @XmlAttribute
    protected String lat;

    public String getLon() {return lon;} public void setLon(String lon) {this.lon = lon;}
    public String getLat() {return lat;} public void setLat(String lat) {this.lat = lat;}
}
