package org.geojsf.doc.ofx;

import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.configuration.Configuration;
import org.geojsf.doc.GeoJsfDocumentation;
import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Service;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.table.OfxCellFactory;
import org.openfuxml.factory.table.OfxColumnFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxLayerTableFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxLayerTableFactory.class);
	
	private static String keyCaption = "geojsfTableLayerCaption";
	private int[] colWidths = {10,20};
	
	private Configuration config;
	private String lang;
	
	public OfxLayerTableFactory(Configuration config,String lang)
	{
		this.config=config;
		this.lang=lang;
	}
	
	public String toLatex(String id, Service service, Layers layers, String[] headerKeys) throws OfxAuthoringException
	{
		try
		{			
			Comment comment = XmlCommentFactory.build();
			DocumentationCommentBuilder.fixedId(comment, id);
			DocumentationCommentBuilder.translationKeys(comment,config,GeoJsfDocumentation.keyTranslationFile);
			DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
			DocumentationCommentBuilder.tableKey(comment,keyCaption,"Table Caption");
			DocumentationCommentBuilder.doNotModify(comment);
			
			Table table = toOfx(headerKeys,getLayersInService(service, layers));
			table.setId(id);
			table.setComment(comment);
			
	//		Lang lCaption = StatusXpath.getLang(translations, captionKey, lang);
	//		table.setTitle(XmlTitleFactory.build(lCaption.getTranslation()));
			table.setTitle(XmlTitleFactory.build("test Title"));
			
			LatexTableRenderer renderer = new LatexTableRenderer(false);
			renderer.render(table);
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			return actual.toString();
		}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public Table toOfx(String[] headerKeys, Layers layers) throws OfxAuthoringException
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		
		try{table.setContent(createContent(headerKeys,layers));}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		
		return table;
	}
	
	private Specification createSpecifications()
	{

		Columns cols = new Columns();

		cols.getColumn().add(OfxColumnFactory.percentage(colWidths[0]));
		cols.getColumn().add(OfxColumnFactory.flex(colWidths[1]));
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		return specification;
	}
	
	private Content createContent(String[] headerKeys, Layers layers) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Row row = new Row();
		for(String headerKey : headerKeys)
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(lang+"-"+headerKey));
		}
		
		Head head = new Head();
		head.getRow().add(row);
		
		Body body = new Body();
		
		for(Layer layer : layers.getLayer())
		{

			body.getRow().add(createRow(layer));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(Layer layer) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{		
		Row row = new Row();
		
		row.getCell().add(OfxCellFactory.createParagraphCell(layer.getCode()));
		row.getCell().add(OfxCellFactory.createParagraphCell(StatusXpath.getLang(layer.getLangs(), lang).getTranslation()));

		return row;
	}
	
	private Layers getLayersInService(Service service, Layers allLayers)
	{
		Layers layers = new Layers();
		
		for(Layer layer : allLayers.getLayer())
		{
			if(layer.getService().getCode().equals(service.getCode()))
			{
				layers.getLayer().add(layer);
			}
		}
		
		return layers;
	}
}