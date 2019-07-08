package org.geojsf.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.geojsf.controller.util.GeoJsfSldConfigurationProvider;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractSldLibraryServlet<L extends UtilsLang,D extends UtilsDescription,
										G extends JeeslGraphic<L,D,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
										F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
										LAYER extends GeoJsfLayer<L,D,?,?,?,?,SLD>,
										MAP extends GeoJsfMap<L,D,?,?,?>,
										SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
										SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
										SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
										RULE extends GeoJsfSldRule<L,D,G>>
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