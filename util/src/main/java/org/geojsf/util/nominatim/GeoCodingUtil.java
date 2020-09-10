package org.geojsf.util.nominatim;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.geojsf.model.xml.nominator.NominatorPlace;
import org.geojsf.model.xml.nominator.NominatorSearchResults;
import org.geojsf.util.network.HttpUtil;
import com.vividsolutions.jts.geom.Point;
import org.geojsf.factory.wkt.PointFactory;

public class GeoCodingUtil {
	
    public static String getCoordinates(String locationString) throws JAXBException
    {
	String nomatim = "https://nominatim.openstreetmap.org/search?q=";

	Unmarshaller unmarshaller = null;
	JAXBContext context = JAXBContext.newInstance(NominatorSearchResults.class, NominatorPlace.class); 
	unmarshaller        = context.createUnmarshaller();

	String request = locationString +"&" +"format=xml";

	String result = HttpUtil.getContentAsText(nomatim +request);
	System.out.println(result);
	StringReader reader = new StringReader(result);
	NominatorSearchResults nominator = (NominatorSearchResults) unmarshaller.unmarshal(reader);

	return nominator.getPlace().getLat() +"," +nominator.getPlace().getLon();
    }

    public static Point getPoint(String locationString) throws JAXBException
    {
	String nomatim = "https://nominatim.openstreetmap.org/search?q=";

	Unmarshaller unmarshaller = null;
	JAXBContext context = JAXBContext.newInstance(NominatorSearchResults.class, NominatorPlace.class); 
	unmarshaller        = context.createUnmarshaller();

	String request = locationString +"&" +"format=xml";

	String result = HttpUtil.getContentAsText(nomatim +request);
	System.out.println(result);
	StringReader reader = new StringReader(result);
	NominatorSearchResults nominator = (NominatorSearchResults) unmarshaller.unmarshal(reader);
	PointFactory pf = new PointFactory();
	Point point = pf.build(new Double(nominator.getPlace().getLat()),new Double(nominator.getPlace().getLon()));
	return point;
    }
}
