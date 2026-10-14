package org.geojsf.model.xml.nominator;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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
