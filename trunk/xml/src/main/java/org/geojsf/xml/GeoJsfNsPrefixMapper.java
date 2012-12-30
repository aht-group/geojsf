package org.geojsf.xml;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class GeoJsfNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
    	if("http://geojsf.sf.net/geojsf".equals(namespaceUri) ){return "geojsf";}
    	if("http://www.geojsf.org/geoserver".equals(namespaceUri) ){return "geoserver";}
    	if("http://geojsf.sf.net/openlayers".equals(namespaceUri) ){return "ol";}
    	if("http://www.opengis.net/gml".equals(namespaceUri) ){return "gml";}
    	if("http://www.opengis.net/wfs".equals(namespaceUri) ){return "wfs";}
    	
    	if("http://ahtutils.aht-group.com/status".equals(namespaceUri) ){return "us";}
    	if("http://exlp.sf.net/net".equals(namespaceUri) ){return "net";}
    	if("http://exlp.sf.net/identity".equals(namespaceUri) ){return "id";}
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