package org.geojsf.util.wms;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Service;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WmsTileDownloader
{
	final static Logger logger = LoggerFactory.getLogger(WmsTileDownloader.class);
	
	private final int inchesPerUnit = 4374754;
	
	private Service service;
	
	private int width;
	private int height;
	
	public WmsTileDownloader(Service service)
	{
		this.service=service;
		width=600;
		height=400;
	}

	public void download(Map map, File fDst)
	{
		String urlRequest = calculateRequest(map);
		logger.info("Request: "+urlRequest);
		
		try
		{
			URL url = new URL(urlRequest);
			FileUtils.copyURLToFile(url,fDst);
		}
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public String calculateRequest(Map map)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(service.getWms());
		sb.append("?service=WMS&version=1.1.0&request=GetMap&layers=cblt.dss:");
		
		for(int i=map.getView().size();i>0;i--)
		{
			sb.append(map.getView().get(i-1).getLayer().getCode()).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		
		logger.warn("Width/Hegh NYI"+width+"/"+height);
//		calculateBoundingBox(map.getViewPort(),width,height);
		
		JaxbUtil.info(map.getViewPort());
		
		sb.append("&bbox=");
		sb.append(map.getViewPort().getLeft()).append(",");
		sb.append(map.getViewPort().getBottom()).append(",");
		sb.append(map.getViewPort().getRight()).append(",");
		sb.append(map.getViewPort().getTop());

		sb.append("&width=800&height=600&format=image/png");
		
		return sb.toString();
	}
	
	public double calculateResolution(double scale, int dotsPerInch)
	{
		double div = inchesPerUnit * dotsPerInch;
		return scale / div;
	}
	
	public void calculateBoundingBox(ViewPort viewPort, int width, int height)
	{	
		double resolution = calculateResolution(viewPort.getScale().getValue(),72);
		
		double halfWidth = (width * resolution) / 2;
		double halfHeight = (height * resolution) / 2;
		
		viewPort.setLeft(viewPort.getLon()-halfWidth);
		viewPort.setRight(viewPort.getLon()+halfWidth);
		viewPort.setTop(viewPort.getLat()+halfHeight);
		viewPort.setBottom(viewPort.getLat()-halfHeight);
	}
	
	public void setWidth(int width) {this.width = width;}
	public void setHeight(int height) {this.height = height;}
}