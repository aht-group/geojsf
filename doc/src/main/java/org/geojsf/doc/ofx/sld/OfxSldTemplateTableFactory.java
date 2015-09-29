package org.geojsf.doc.ofx.sld;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.geojsf.doc.GeoJsfDocumentation;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.SldTemplate;
import org.openfuxml.content.ofx.Comment;
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
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.util.OfxMultiLangFactory;
import net.sf.ahtutils.xml.status.Translation;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class OfxSldTemplateTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSldTemplateTableFactory.class);
	
	private static String keyCaption = "geojsfTableLayerCaptionPrefix";
	private List<String> headerKeys;
	
	public OfxSldTemplateTableFactory(Configuration config,String[] langs,Translations translations)
	{
		super(config,langs,translations);
		
		headerKeys = new ArrayList<String>();
		headerKeys.add("auTableStatusCode");
		headerKeys.add("auTableStatusName");
		headerKeys.add("auTableStatusDescription");
	}
	
	public Table build(String id, Repository repository) throws OfxAuthoringException
	{	
		Table table = toOfx(repository.getSldTemplate());
		table.setId(id);
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.fixedId(comment, id);
		DocumentationCommentBuilder.translationKeys(comment,config,GeoJsfDocumentation.keyTranslationFile);
		DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
		DocumentationCommentBuilder.tableKey(comment,keyCaption,"Table Caption");
		OfxCommentBuilder.doNotModify(comment);
		table.setComment(comment);
		
		try
		{
			Translation t = StatusXpath.getTranslation(translations, "geojsfSldTemplatesTableCaption");
			table.setTitle(OfxMultiLangFactory.title(langs, t.getLangs()));
		}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
		
			
		return table;
	}
	
	private Table toOfx(List<SldTemplate> templates) throws OfxAuthoringException
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		table.setContent(createContent(templates));
		
		return table;
	}
	
	private Specification createSpecifications()
	{
		Specification specification = new Specification();
		specification.setFloat(XmlFloatFactory.build(false));
		
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.flex(20));
		cols.getColumn().add(OfxColumnFactory.flex(20));
		cols.getColumn().add(OfxColumnFactory.flex(60));
		specification.setColumns(cols);
		
		return specification;
	}
	
	private Content createContent(List<SldTemplate> templates) throws OfxAuthoringException
	{	
		Head head = new Head();
		head.getRow().add(this.createHeaderRow(headerKeys));
		
		Body body = new Body();
		
		for(SldTemplate template : templates)
		{
			body.getRow().add(createRow(template));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(SldTemplate template) throws OfxAuthoringException
	{		
		Row row = new Row();
		
		row.getCell().add(OfxCellFactory.createParagraphCell(template.getCode()));
		
		Cell cName = OfxCellFactory.build();
		cName.getContent().addAll(OfxMultiLangFactory.paragraph(langs, template.getLangs()));
		row.getCell().add(cName);
		
		Cell cDescription = OfxCellFactory.build();
		cDescription.getContent().addAll(OfxMultiLangFactory.paragraph(langs, template.getDescriptions()));
		row.getCell().add(cDescription);

		return row;
	}
}