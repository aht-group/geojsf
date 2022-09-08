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
import org.geojsf.model.xml.nominator.NominatorReverseGeocode;

public class GeoCodingUtil {
	
    public static String getCoordinates(String locationString) throws JAXBException
    {
		String nomatim = "https://nominatim.openstreetmap.org/search?q=";

		Unmarshaller unmarshaller = null;
		JAXBContext context = JAXBContext.newInstance(NominatorSearchResults.class, NominatorPlace.class); 
		unmarshaller        = context.createUnmarshaller();

		String request = locationString +"&" +"format=xml";
		String preparedRequest = nomatim +request;
		// System.out.println(preparedRequest);
		String result = HttpUtil.getContentAsText(preparedRequest);
		// System.out.println("Server result: " +result);
		StringReader reader = new StringReader(result);
		NominatorSearchResults nominator = (NominatorSearchResults) unmarshaller.unmarshal(reader);

		return nominator.getPlace().getLat() +"," +nominator.getPlace().getLon();
    }

	public static String getPlaceInfoString(Double lat, Double lon) throws JAXBException
    {
		String nomatim = "https://nominatim.openstreetmap.org/reverse?";

		Unmarshaller unmarshaller = null;
		JAXBContext context = JAXBContext.newInstance(NominatorReverseGeocode.class); 
		unmarshaller        = context.createUnmarshaller();

		String request = "lat=" +lat +"&lon=" +lon +"&format=xml";
		String preparedRequest = nomatim +request;
		// System.out.println(preparedRequest);
		String result = HttpUtil.getContentAsText(preparedRequest);
		// System.out.println("Server result: " +result);
		StringReader reader = new StringReader(result);
		NominatorReverseGeocode nominator = (NominatorReverseGeocode) unmarshaller.unmarshal(reader);

		return nominator.getLocationString();
    }

    public static Point getPoint(String locationString) throws JAXBException
    {
		String nomatim = "https://nominatim.openstreetmap.org/search?q=";

		Unmarshaller unmarshaller = null;
		JAXBContext context = JAXBContext.newInstance(NominatorSearchResults.class, NominatorPlace.class); 
		unmarshaller        = context.createUnmarshaller();

		String request = locationString +"&" +"format=xml";

		String result = HttpUtil.getContentAsText(nomatim +request);
		// System.out.println(result);
		StringReader reader = new StringReader(result);
		NominatorSearchResults nominator = (NominatorSearchResults) unmarshaller.unmarshal(reader);
		PointFactory pf = new PointFactory();
		Point point = pf.build(new Double(nominator.getPlace().getLat()),new Double(nominator.getPlace().getLon()));
		return point;
    }
}
