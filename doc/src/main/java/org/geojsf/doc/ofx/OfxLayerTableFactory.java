package org.geojsf.doc.ofx;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.geojsf.doc.GeoJsfDocumentation;
import org.geojsf.model.xml.geojsf.Category;
import org.geojsf.model.xml.geojsf.Layer;
import org.jeesl.util.query.xpath.StatusXpath;
import org.openfuxml.content.layout.Width;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.media.Media;
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
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class OfxLayerTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxLayerTableFactory.class);
	
	private static String keyCaption = "geojsfTableLayerCaptionPrefix";
		
	public OfxLayerTableFactory(Configuration config, String lang, Translations translations)
	{
		this(config,new String[] {lang},translations);
	}
	public OfxLayerTableFactory(Configuration config,String[] langs,Translations translations)
	{
		super(config,langs,translations);
	}
	
	public Table build(String id, Category category, List<String> headerKeys) throws OfxAuthoringException, UtilsConfigurationException
	{	
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.fixedId(comment, id);
		DocumentationCommentBuilder.translationKeys(comment,config,GeoJsfDocumentation.keyTranslationFile);
		DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
		DocumentationCommentBuilder.tableKey(comment,keyCaption,"Table Caption");
		OfxCommentBuilder.doNotModify(comment);
		
		Table table = toOfx(headerKeys,category.getLayer());
		table.setId(id);
		table.setComment(comment);
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Lang lCaption = StatusXpath.getLang(translations, keyCaption, langs[0]);
			Lang lService = StatusXpath.getLang(category.getLangs(), langs[0]);
			
			table.setTitle(XmlTitleFactory.build(lCaption.getTranslation()+": "+lService.getTranslation()));
		}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}	
		
		return table;
	}
	
	private Table toOfx(List<String> headerKeys, List<Layer> layers) throws OfxAuthoringException
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
		Specification specification = new Specification();
		specification.setFloat(XmlFloatFactory.build(false));
		
		Columns cols = new Columns();
//		cols.getColumn().add(OfxColumnFactory.percentage(30));
//		cols.getColumn().add(OfxColumnFactory.flex(100));
		cols.getColumn().add(OfxColumnFactory.flex(15));
		cols.getColumn().add(OfxColumnFactory.flex(85));
		specification.setColumns(cols);
		
		return specification;
	}
	
	private Content createContent(List<String> headerKeys, List<Layer> layers) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{	
		Head head = new Head();
		head.getRow().add(this.createHeaderRow(headerKeys));
		
		Body body = new Body();
		
		for(Layer layer : layers)
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
		
//		row.getCell().add(cellImage(layer));
		row.getCell().add(OfxCellFactory.createParagraphCell(layer.getCode()));
		
		row.getCell().add(cellDescription(layer));		

		return row;
	}
	
	@SuppressWarnings("unused")
	private Cell cellImage(Layer layer)
	{
		Media media = new Media();
		media.setDst("gis/layer/"+layer.getCode().replaceAll("\\.", ""));
		Image image = new Image();
		image.setMedia(media);
		
		Width width = new Width();
		width.setUnit("cm");
		width.setValue(2);
		image.setWidth(width);
		
		
		Cell cell = new Cell();
		cell.getContent().add(image);
		return cell;
	}
	
	private Cell cellDescription(Layer layer)
	{
		String text,description;
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Lang l = StatusXpath.getLang(layer.getLangs(), langs[0]);
			text = l.getTranslation();
		}
		catch (ExlpXpathNotFoundException e){text = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){text = e.getMessage();}
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Description d = StatusXpath.getDescription(layer.getDescriptions(), langs[0]);
			description = d.getValue();
		}
		catch (ExlpXpathNotFoundException e){description = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){description = e.getMessage();}
		
		Paragraph p1 = new Paragraph();
		p1.getContent().add(text+" ("+layer.getCode()+")");
		
		Paragraph p = new Paragraph();
		p.getContent().add(description);
		
		Cell cellDescription = new Cell();
		cellDescription.getContent().add(p1);
		cellDescription.getContent().add(p);
		
		return cellDescription;
	}
}