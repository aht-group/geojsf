package org.geojsf.factory.image;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Point;

import org.geojsf.interfaces.util.with.EjbWithGeometry;
import org.geojsf.util.network.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapImageLoader {
    
    final static Logger logger = LoggerFactory.getLogger(MapImageLoader.class);
    
    private String wmsServiceUrl;
    private String layers;
    
    public MapImageLoader(String wmsServiceUrl, String layerString)
    {
	this.wmsServiceUrl  = wmsServiceUrl;
	this.layers	    = layerString;
    }
    
    /**
     * Create a SVG image holding a map centered at the location of the entity and expanded to the given size incl. a circle marker in the center with given radius (set to 0.0 if not needed)
     * @param entity An entity implementing {@link org.geojsf.interfaces.util.with.EjbWithGeometry EjbWithGeometry} with a {@link com.vividsolutions.jts.geom.Point Point} as Geometry
     * @param mapSize The radius of the {@link com.vividsolutions.jts.geom.Envelope Envelope} envelope
     * @param centerMarkerPointRadius Radius of the point marking the center
     * @return The XML representation of the SVG as text
     */
    public String getSvgXmlWithCenteredCircleMarker(EjbWithGeometry entity, Double mapSize, Double centerMarkerPointRadius)
    {
	Point centerPoint = (Point) entity.getGeometry();
	Envelope env = new Envelope(centerPoint.getCoordinate());
	env.expandBy(mapSize);
	StringBuilder requestUrl = new StringBuilder();
	requestUrl.append(wmsServiceUrl);
	requestUrl.append("?TILED=true&srs=EPSG%3A4326&WIDTH=256&HEIGHT=256&SERVICE=WMS&VERSION=1.1.0&REQUEST=GetMap&TRANSPARENT=true&format=image/svg&LAYERS=" +layers +"&");
	requestUrl.append("BBOX=" + env.getMinX() +"," +env.getMinY() +"," +env.getMaxX() +"," +env.getMaxY());
	logger.info("Requesting SVG Map from WMS using " +requestUrl.toString());
	String svgXml = "";
	svgXml = HttpUtil.getContentAsText(requestUrl.toString());
	String pointMarker = "<circle cx=\"50%\" cy=\"50%\" r=\"" +centerMarkerPointRadius +"\" stroke=\"gray\" fill=\"solid\" stroke-width=\"2\"/> \n +</svg>";
	svgXml = svgXml.replace("</svg>", pointMarker);
	return svgXml;
    }
    
}
