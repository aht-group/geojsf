package org.geojsf.util.wms;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.geojsf.factory.xml.geojsf.XmlScaleFactory;
import org.geojsf.factory.xml.geojsf.XmlViewPortFactory;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Service;
import org.geojsf.xml.geojsf.ViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WmsTileDownloader
{
	final static Logger logger = LoggerFactory.getLogger(WmsTileDownloader.class);
	
	private final int inchesPerUnit = 4374754;
	
	private Service service;
	
	public WmsTileDownloader(Service service)
	{
		this.service=service;
	}
	
	public void download(Map map, File fDst)
	{
		String urlRequest = calculateRequest(map);
		
		logger.info("Request: "+urlRequest);
		
		try {
			URL url = new URL(urlRequest);
			FileUtils.copyURLToFile(url,fDst);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String calculateRequest(Map map)
	{
		int width=600;
		int height=400;
		
		StringBuffer sb = new StringBuffer();
		sb.append(service.getUrl());
		sb.append("?service=WMS&version=1.1.0&request=GetMap&layers=cblt.dss:");
		
		for(int i=map.getView().size();i>0;i--)
		{
			sb.append(map.getView().get(i-1).getLayer().getCode()).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		
		
		ViewPort viewPort = XmlViewPortFactory.build(map.getLon(), map.getLat());
		viewPort.setScale(XmlScaleFactory.build(400000));
		calculateBoundingBox(viewPort,width,height);
		
		sb.append("&bbox=");
		sb.append(viewPort.getLeft()).append(",");
		sb.append(viewPort.getRight()).append(",");
		sb.append(viewPort.getTop()).append(",");
		sb.append(viewPort.getBottom());

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
		viewPort.setTop(viewPort.getLat()-halfHeight);
		viewPort.setBottom(viewPort.getLat()+halfHeight);
	}
}