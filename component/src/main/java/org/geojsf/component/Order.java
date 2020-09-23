package org.geojsf.component;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    public void Order()
    {
    }

    private static final long serialVersionUID = 4707371203319566564L;

    private Long              serviceId;
    private ArrayList<String> layer;
    private String            command;

    public String getCommand() {return command;}
    public void setCommand(String command) {this.command = command;}
    public Long getServiceId() {return serviceId;}
    public void setServiceId(Long serviceId2) {this.serviceId = serviceId2;}
    public ArrayList<String> getLayer() {return layer;}
    public void setLayer(ArrayList<String> layer) {this.layer = layer;}

}