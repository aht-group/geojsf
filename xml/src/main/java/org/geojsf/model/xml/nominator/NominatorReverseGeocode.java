package org.geojsf.model.xml.nominator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "reversegeocode")
public class NominatorReverseGeocode {

    @XmlElement(name = "addressparts")
    protected NominatorAddressParts addressParts;

	@XmlElement(name = "result")
	protected String locationString;

    public NominatorAddressParts getAddressParts() {return addressParts;} public void setAddressParts(NominatorAddressParts addressParts) {this.addressParts = addressParts;}
	public String getLocationString() {return locationString;} public void setLocationString(String locationString) {this.locationString = locationString;}
}
