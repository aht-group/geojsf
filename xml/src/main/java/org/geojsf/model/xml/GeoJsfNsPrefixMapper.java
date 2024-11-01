package org.geojsf.model.xml;

import org.exlp.interfaces.io.NsPrefixMapperInterface;

public class GeoJsfNsPrefixMapper //extends org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper
									implements NsPrefixMapperInterface
{
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
    	if("http://www.geojsf.org".equals(namespaceUri) ){return "geojsf";}
    	if("http://www.geojsf.org/geoserver".equals(namespaceUri) ){return "g";}
    	if("http://www.geojsf.org/monitoring".equals(namespaceUri) ){return "m";}
    	if("http://www.geojsf.org/area".equals(namespaceUri) ){return "a";}
    	
    	
    	if("http://www.opengis.net/gml".equals(namespaceUri) ){return "gml";}
    	if("http://www.opengis.net/wfs".equals(namespaceUri) ){return "wfs";}
    	if("http://www.opengis.net/ogc".equals(namespaceUri) ){return "ogc";}
    	if("http://www.opengis.net/sld".equals(namespaceUri) ){return "sld";}
    	if("http://www.opengis.net/se".equals(namespaceUri) ){return "se";}
    	if("http://www.w3.org/1999/xlink".equals(namespaceUri) ){return "xlink";}
    	
    	
    	if("http://ahtutils.aht-group.com/status".equals(namespaceUri) ){return "us";}
    	if("http://www.jeesl.org/symbol".equals(namespaceUri) ){return "jsym";}
    	if("http://exlp.sf.net/net".equals(namespaceUri) ){return "net";}
    	if("http://exlp.sf.net/identity".equals(namespaceUri) ){return "id";}
    	
    	if("http://www.openfuxml.org".equals(namespaceUri) ){return "ofx";}
    	if("http://www.openfuxml.org/table".equals(namespaceUri) ){return "ofxt";}
    	if("http://www.openfuxml.org/text".equals(namespaceUri) ){return "ofxtx";}
    	
        return suggestion;
    }

    public String[] getPreDeclaredNamespaceUris()
    {
    	String[] result = new String[3];
    	result[2] = "http://geojsf.sf.net";
    	result = new String[0];
        return result;
    }
}