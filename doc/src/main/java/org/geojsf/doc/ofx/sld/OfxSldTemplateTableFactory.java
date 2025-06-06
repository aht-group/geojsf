package org.geojsf.doc.ofx.sld;

import java.util.ArrayList;
import java.util.List;

import org.geojsf.doc.GeoJsfDocumentation;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.SldTemplate;
import org.jeesl.doc.ofx.OfxMultiLangFactory;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.model.xml.io.locale.status.Translation;
import org.jeesl.model.xml.io.locale.status.Translations;
import org.jeesl.util.query.xpath.StatusXpath;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.table.XmlCellFactory;
import org.openfuxml.factory.xml.table.XmlColumnFactory;
import org.openfuxml.model.xml.core.ofx.Comment;
import org.openfuxml.model.xml.core.table.Body;
import org.openfuxml.model.xml.core.table.Cell;
import org.openfuxml.model.xml.core.table.Columns;
import org.openfuxml.model.xml.core.table.Content;
import org.openfuxml.model.xml.core.table.Head;
import org.openfuxml.model.xml.core.table.Row;
import org.openfuxml.model.xml.core.table.Specification;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class OfxSldTemplateTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSldTemplateTableFactory.class);
	
	private static String keyCaption = "geojsfTableLayerCaptionPrefix";
	private List<String> headerKeys;
	
	public OfxSldTemplateTableFactory(org.exlp.interfaces.system.property.Configuration config, String[] langs,Translations translations)
	{
		super(config,langs,translations);
		
		headerKeys = new ArrayList<String>();
		headerKeys.add("auTableStatusCode");
		headerKeys.add("auTableStatusName");
		headerKeys.add("auTableStatusDescription");
	}

	
	public Table build(String id, Repository repository) throws OfxAuthoringException, UtilsConfigurationException
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
		cols.getColumn().add(XmlColumnFactory.flex(20));
		cols.getColumn().add(XmlColumnFactory.flex(20));
		cols.getColumn().add(XmlColumnFactory.flex(60));
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
		
		row.getCell().add(XmlCellFactory.createParagraphCell(template.getCode()));
		
		Cell cName = XmlCellFactory.build();
		cName.getContent().addAll(OfxMultiLangFactory.paragraph(langs, template.getLangs()));
		row.getCell().add(cName);
		
		Cell cDescription = XmlCellFactory.build();
		cDescription.getContent().addAll(OfxMultiLangFactory.paragraph(langs, template.getDescriptions()));
		row.getCell().add(cDescription);

		return row;
	}
}