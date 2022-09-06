/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geojsf.model.json;

import java.util.List;

/**
 *
 * @author helgehemmer
 */
public class MapData {
    
    private MapConfiguration configuration;
    private List<WmsService> services;
    private List<WmsLayer>   layers;

    public MapConfiguration getConfiguration() {return configuration;}
    public void setConfiguration(MapConfiguration configuration) {this.configuration = configuration;}

    public List<WmsService> getServices() {return services;}
    public void setServices(List<WmsService> services) {this.services = services;}

    public List<WmsLayer> getLayers() {return layers;}
    public void setLayers(List<WmsLayer> layers) {this.layers = layers;}
    
}
