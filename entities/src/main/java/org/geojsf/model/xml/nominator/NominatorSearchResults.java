package org.geojsf.model.xml.nominator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "searchresults")
public class NominatorSearchResults {
	
    @XmlElement(name = "place")
    protected NominatorPlace place;

    public NominatorPlace getPlace() {return place;} public void setPlace(NominatorPlace place) {this.place = place;}
}
