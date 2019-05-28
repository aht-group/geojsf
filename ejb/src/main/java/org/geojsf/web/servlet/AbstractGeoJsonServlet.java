package org.geojsf.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractGeoJsonServlet extends HttpServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsonServlet.class);
	
	protected String getPathInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getPathInfo() == null)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

        String path = URLDecoder.decode(request.getPathInfo(), "UTF-8");
        if(path.length()<1)
        {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return null;
        }
        
        logger.error("Paht: "+path);
        
        String[] pathElements = path.split("/");
        return pathElements[1];
	}
	
	protected void respond(HttpServletRequest request, HttpServletResponse response, byte[] bytes) throws ServletException, IOException
    {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		
		response.reset();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Content-Length", String.valueOf(bytes.length));
		
	  	IOUtils.copy(bais,response.getOutputStream());
	}
}