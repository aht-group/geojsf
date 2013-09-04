
package org.geojsf.xml.geoserver;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.geojsf.xml.geoserver package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.geojsf.xml.geoserver
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Connection }
     * 
     */
    public Connection createConnection() {
        return new Connection();
    }

    /**
     * Create an instance of {@link ShapeDir }
     * 
     */
    public ShapeDir createShapeDir() {
        return new ShapeDir();
    }

    /**
     * Create an instance of {@link Spatial }
     * 
     */
    public Spatial createSpatial() {
        return new Spatial();
    }

    /**
     * Create an instance of {@link Workspace }
     * 
     */
    public Workspace createWorkspace() {
        return new Workspace();
    }

    /**
     * Create an instance of {@link Layers }
     * 
     */
    public Layers createLayers() {
        return new Layers();
    }

    /**
     * Create an instance of {@link Layer }
     * 
     */
    public Layer createLayer() {
        return new Layer();
    }

    /**
     * Create an instance of {@link CoverageStore }
     * 
     */
    public CoverageStore createCoverageStore() {
        return new CoverageStore();
    }

    /**
     * Create an instance of {@link Styles }
     * 
     */
    public Styles createStyles() {
        return new Styles();
    }

    /**
     * Create an instance of {@link Style }
     * 
     */
    public Style createStyle() {
        return new Style();
    }

    /**
     * Create an instance of {@link Postgis }
     * 
     */
    public Postgis createPostgis() {
        return new Postgis();
    }

    /**
     * Create an instance of {@link DataStore }
     * 
     */
    public DataStore createDataStore() {
        return new DataStore();
    }

    /**
     * Create an instance of {@link DataStores }
     * 
     */
    public DataStores createDataStores() {
        return new DataStores();
    }

    /**
     * Create an instance of {@link Workspaces }
     * 
     */
    public Workspaces createWorkspaces() {
        return new Workspaces();
    }

    /**
     * Create an instance of {@link CoverageStores }
     * 
     */
    public CoverageStores createCoverageStores() {
        return new CoverageStores();
    }

}
