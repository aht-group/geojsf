package org.geojsf.doc.ofx;

import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.configuration.Configuration;
import org.geojsf.doc.GeoJsfDocumentation;
import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Service;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.table.OfxCellFactory;
import org.openfuxml.factory.table.OfxColumnFactory;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxSectionLayerFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSectionLayerFactory.class);
	
	private static String keyCaption = "geojsfTableLayerCaptionPrefix";
		
	public OfxSectionLayerFactory(Configuration config,String lang,Translations translations)
	{
		super(config,lang,translations);
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
			
			try
			{
				Lang lCaption = StatusXpath.getLang(translations, keyCaption, lang);
				Lang lService = StatusXpath.getLang(service.getLangs(), lang);
				
				table.setTitle(XmlTitleFactory.build(lCaption.getTranslation()+" "+lService.getTranslation()));
			}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}	
			
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

//		cols.getColumn().add(OfxColumnFactory.percentage(30));
//		cols.getColumn().add(OfxColumnFactory.flex(100));
		
		cols.getColumn().add(OfxColumnFactory.flex(25));
		cols.getColumn().add(OfxColumnFactory.flex(75));
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		specification.setFloat(XmlFloatFactory.build(false));
		return specification;
	}
	
	private Content createContent(String[] headerKeys, Layers layers) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{	
		Head head = new Head();
		head.getRow().add(this.createHeaderRow(headerKeys));
		
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
		
		String text,description;
		
		try
		{
			Lang l = StatusXpath.getLang(layer.getLangs(), lang);
			text = l.getTranslation();
		}
		catch (ExlpXpathNotFoundException e){text = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){text = e.getMessage();}
		
		try
		{
			Description d = StatusXpath.getDescription(layer.getDescriptions(), lang);
			description = d.getValue();
		}
		catch (ExlpXpathNotFoundException e){description = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){description = e.getMessage();}
		
		Paragraph p1 = new Paragraph();
		p1.getContent().add(text);
		
		Paragraph p2 = new Paragraph();
		p2.getContent().add("("+layer.getCode()+")");
		
		Cell cell = new Cell();
		cell.getContent().add(p1);
		cell.getContent().add(p2);
		
		row.getCell().add(cell);
		
//		row.getCell().add(OfxCellFactory.createParagraphCell(layer.getCode()));
//		row.getCell().add(OfxCellFactory.createParagraphCell(text));
		
		row.getCell().add(OfxCellFactory.createParagraphCell(description));

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