package org.geojsf.model.xml.nominator;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "searchresults")
public class NominatorSearchResults {
	
    @XmlElement(name = "place")
    protected NominatorPlace place;

    public NominatorPlace getPlace() {return place;} public void setPlace(NominatorPlace place) {this.place = place;}
}
