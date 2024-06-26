package org.geojsf.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.util.GeoJsfSldConfigurationProvider;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractSldLibraryServlet<L extends JeeslLang,D extends JeeslDescription,
										LAYER extends GeoJsfLayer<L,D,?,?,?,?,?,SLD>,
										MAP extends GeoJsfMap<L,D,?,?,?>,
										SDX extends GeoJsfSldXml<L,D,SLD>,
										SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
										SLD extends GeoJsfSld<L,D,SDX,SLDTYPE,RULE,?,?>,
										RULE extends GeoJsfSldRule<L,D,?>>
				extends HttpServlet
				implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSldLibraryServlet.class);

	protected GeoJsfSldConfigurationProvider sldConfigurationProvider;
	
	public AbstractSldLibraryServlet()
	{
		
	}
	
	protected void respond(HttpServletRequest request, HttpServletResponse response,byte[] bytes, String suffix) throws ServletException, IOException
    {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		
		response.reset();
		response.setContentType(getServletContext().getMimeType("x."+suffix));
		response.setHeader("Content-Length", String.valueOf(bytes.length));
		
	  	IOUtils.copy(bais,response.getOutputStream());
	}
}