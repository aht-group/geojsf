package org.geojsf.web.servlet;

import java.io.Serializable;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSldLibraryServlet
	extends HttpServlet
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSldLibraryServlet.class);
	
	protected boolean debugOnInfo; protected void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo=debugOnInfo;}
	
	protected AbstractSldLibraryServlet()
	{
		debugOnInfo = false;
	}
	

}