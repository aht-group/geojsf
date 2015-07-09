package org.geojsf.factory.svg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SvgSldRuleFactory<L extends UtilsLang,
									D extends UtilsDescription,
									TYPE extends UtilsStatus<TYPE,L,D>,
									STYLE extends UtilsStatus<STYLE,L,D>,
									SLD extends GeoJsfSld<L,D,TYPE,STYLE,SLD,RULE,TEMPLATE>,
									RULE extends GeoJsfSldRule<L,D,TYPE,STYLE,SLD,RULE,TEMPLATE>,
									TEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,TEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(SvgSldRuleFactory.class);
		
	private DOMImplementation impl;
	
	public SvgSldRuleFactory()
	{
		impl = SVGDOMImplementation.getDOMImplementation();
	}
	
    public static <L extends UtilsLang,
					D extends UtilsDescription,
					TYPE extends UtilsStatus<TYPE,L,D>,
					STYLE extends UtilsStatus<STYLE,L,D>,
					SLD extends GeoJsfSld<L,D,TYPE,STYLE,SLD,RULE,TEMPLATE>,
					RULE extends GeoJsfSldRule<L,D,TYPE,STYLE,SLD,RULE,TEMPLATE>,
					TEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,TEMPLATE>>
    	SvgSldRuleFactory<L,D,TYPE,STYLE,SLD,RULE,TEMPLATE> factory()
	{
	    return new SvgSldRuleFactory<L,D,TYPE,STYLE,SLD,RULE,TEMPLATE>();
	}
    
	public static SVGGraphics2D build()
	{
		// Create an SVG document.
	    DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
	    SVGDocument doc = (SVGDocument) impl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);

	    // Create a converter for this document.
	    SVGGraphics2D g = new SVGGraphics2D(doc);

	    // Do some drawing.
	    Shape circle = new Ellipse2D.Double(0, 0, 50, 50);
	    g.setPaint(Color.red);
	    g.fill(circle);
	    g.translate(60, 0);
	    g.setPaint(Color.green);
	    g.fill(circle);
	    g.translate(60, 0);
	    g.setPaint(Color.blue);
	    g.fill(circle);
	    g.setSVGCanvasSize(new Dimension(180, 50));
	    
	    return g;
	}
	
	public SVGGraphics2D build(int canvasSize, RULE rule)
	{
		int size = 5;
		if(rule.getSize()!=null){size = rule.getSize();}
		
	    SVGDocument doc = (SVGDocument) impl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
	    SVGGraphics2D g = new SVGGraphics2D(doc);

	    double cS = canvasSize; double s = size;
	    double low = (cS - s)/2;
	    
	    logger.info("Canvas: "+canvasSize+" low:"+low+" size:"+size);
	    
	    // Do some drawing.
	    Shape circle = new Ellipse2D.Double(low, low, size, size);
//	    g.setPaint(Color.decode(rule.getColor()));
	    g.setPaint(Color.red);
	    g.fill(circle);
	    
	    g.setSVGCanvasSize(new Dimension(canvasSize, canvasSize));
	    
	    return g;
	}
}