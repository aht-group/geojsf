
package org.geojsf.model.xml.specs.ogc;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.geojsf.model.xml.specs.ogc package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.geojsf.model.xml.specs.ogc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PropertyName }
     * 
     */
    public PropertyName createPropertyName() {
        return new PropertyName();
    }

    /**
     * Create an instance of {@link Literal }
     * 
     */
    public Literal createLiteral() {
        return new Literal();
    }

    /**
     * Create an instance of {@link Filter }
     * 
     */
    public Filter createFilter() {
        return new Filter();
    }

    /**
     * Create an instance of {@link And }
     * 
     */
    public And createAnd() {
        return new And();
    }

    /**
     * Create an instance of {@link DWithin }
     * 
     */
    public DWithin createDWithin() {
        return new DWithin();
    }

    /**
     * Create an instance of {@link Distance }
     * 
     */
    public Distance createDistance() {
        return new Distance();
    }

    /**
     * Create an instance of {@link PropertyIsBetween }
     * 
     */
    public PropertyIsBetween createPropertyIsBetween() {
        return new PropertyIsBetween();
    }

    /**
     * Create an instance of {@link LowerBoundary }
     * 
     */
    public LowerBoundary createLowerBoundary() {
        return new LowerBoundary();
    }

    /**
     * Create an instance of {@link Function }
     * 
     */
    public Function createFunction() {
        return new Function();
    }

    /**
     * Create an instance of {@link UpperBoundary }
     * 
     */
    public UpperBoundary createUpperBoundary() {
        return new UpperBoundary();
    }

    /**
     * Create an instance of {@link PropertyIsEqualTo }
     * 
     */
    public PropertyIsEqualTo createPropertyIsEqualTo() {
        return new PropertyIsEqualTo();
    }

    /**
     * Create an instance of {@link PropertyIsLessThan }
     * 
     */
    public PropertyIsLessThan createPropertyIsLessThan() {
        return new PropertyIsLessThan();
    }

    /**
     * Create an instance of {@link PropertyIsGreaterThanOrEqualTo }
     * 
     */
    public PropertyIsGreaterThanOrEqualTo createPropertyIsGreaterThanOrEqualTo() {
        return new PropertyIsGreaterThanOrEqualTo();
    }

    /**
     * Create an instance of {@link PropertyIsGreaterThan }
     * 
     */
    public PropertyIsGreaterThan createPropertyIsGreaterThan() {
        return new PropertyIsGreaterThan();
    }

}
