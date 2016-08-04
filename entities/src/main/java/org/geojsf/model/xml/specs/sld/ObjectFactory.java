
package org.geojsf.model.xml.specs.sld;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.geojsf.model.xml.specs.sld package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.geojsf.model.xml.specs.sld
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StyledLayerDescriptor }
     * 
     */
    public StyledLayerDescriptor createStyledLayerDescriptor() {
        return new StyledLayerDescriptor();
    }

    /**
     * Create an instance of {@link NamedLayer }
     * 
     */
    public NamedLayer createNamedLayer() {
        return new NamedLayer();
    }

    /**
     * Create an instance of {@link UserStyle }
     * 
     */
    public UserStyle createUserStyle() {
        return new UserStyle();
    }

}
