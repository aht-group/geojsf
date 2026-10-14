package org.geojsf.model.xml.nominator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "addressparts")
public class NominatorAddressParts {
	
    @XmlElement(name = "house")
    protected String house;
    
    @XmlElement(name = "road")
    protected String road;
    
    @XmlElement(name = "village")
    protected String village;
    
    @XmlElement(name = "town")
    protected String town;
    
    @XmlElement(name = "city")
    protected String city;
    
    @XmlElement(name = "county")
    protected String county;
    
    @XmlElement(name = "postcode")
    protected String postcode;
    
    @XmlElement(name = "country")
    protected String country;
    
    @XmlElement(name = "country_code")
    protected String country_code;

    public String getHouse() {return house;} public void setHouse(String house) {this.house = house;}
    public String getRoad() {return road;} public void setRoad(String road) {this.road = road;}
    public String getVillage() {return village;} public void setVillage(String village) {this.village = village;}
    public String getTown() {return town;} public void setTown(String town) {this.town = town;}
    public String getCity() {return city;} public void setCity(String city) {this.city = city;}
    public String getCounty() {return county;} public void setCounty(String county) {this.county = county;}
    public String getPostcode() {return postcode;} public void setPostcode(String postcode) {this.postcode = postcode;}
    public String getCountry() {return country;} public void setCountry(String country) {this.country = country;}
    public String getCountry_code() {return country_code;} public void setCountry_code(String country_code) {this.country_code = country_code;}
}
