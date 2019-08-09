package org.geojsf.doc.ofx;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.View;
import org.jeesl.util.query.xpath.StatusXpath;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.media.Media;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

public class OfxMapSectionFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxMapSectionFactory.class);
	
	private int[] colWidths = {5,100};
	private String keyTableTitle = "geoJsfTableMapTableTitlePrefix";
	
	public OfxMapSectionFactory(Configuration config, String lang, Translations translations)
	{
		this(config,new String[] {lang},translations);
	}
	public OfxMapSectionFactory(Configuration config,String[] langs, Translations translations)
	{
		super(config,langs,translations);
	}
	
	public Section create(Map map,List<String> headerKeys) throws OfxAuthoringException
	{
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		
		Section section = XmlSectionFactory.build();
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Lang l = StatusXpath.getLang(map.getLangs(), langs[0]);
			section.getContent().add(XmlTitleFactory.build(l.getTranslation()));
		}
		catch (ExlpXpathNotFoundException e){throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e){throw new OfxAuthoringException(e.getMessage());}
		
		section.getContent().add(buildIntrodruction(map));
		section.getContent().add(buildTable(headerKeys,map));
		section.getContent().add(buildImage(map));
		
		JaxbUtil.trace(section);
		return section;
	}
	
	private Paragraph buildIntrodruction(Map map) throws OfxAuthoringException
	{
		Paragraph p = XmlParagraphFactory.build();
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Description d = StatusXpath.getDescription(map.getDescriptions(), langs[0]);
			p.getContent().add(d.getValue());
		}
		catch (ExlpXpathNotFoundException e){throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e){throw new OfxAuthoringException(e.getMessage());}
		
		JaxbUtil.trace(p);
		
		return p;
	}
	
	public Table buildTable(List<String> headerKeys, Map map) throws OfxAuthoringException
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		table.setContent(createContent(headerKeys,map.getView()));
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Lang lPrefix = StatusXpath.getLang(translations, keyTableTitle, langs[0]);
			Lang lSuffix = StatusXpath.getLang(map.getLangs(), langs[0]);

			table.setTitle(XmlTitleFactory.build(lPrefix.getTranslation()+": "+lSuffix.getTranslation()));
		}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
		
		return table;
	}
	
	private Specification createSpecifications()
	{
		Specification specification = new Specification();
		
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.percentage(colWidths[0]));
		cols.getColumn().add(OfxColumnFactory.flex(colWidths[1]));
//		cols.getColumn().add(OfxColumnFactory.flex(colWidths[2]));
		
		specification.setColumns(cols);
		specification.setFloat(XmlFloatFactory.build(false));
		return specification;
	}
	
	private Image buildImage(Map map) throws OfxAuthoringException
	{
		Image image = new Image();
		image.setId("image."+map.getCode());
		image.setAlignment(XmlAlignmentFactory.buildHorizontal(XmlAlignmentFactory.Horizontal.center));
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Lang l = StatusXpath.getLang(map.getLangs(), langs[0]);
			image.setTitle(XmlTitleFactory.build(l.getTranslation()));
		}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
		
		
		Media media = new Media();
	//	media.setSrc("png.dss-doc/maps/"+map.getCode()+".png");
		media.setDst("gis/maps/"+map.getCode());
		image.setMedia(media);
		return image;
	}
	
	private Content createContent(List<String> headerKeys, java.util.List<View> views) throws OfxAuthoringException
	{		
		Head head = new Head();
		head.getRow().add(super.createHeaderRow(headerKeys));
		
		Body body = new Body();
		
		for(View view : views)
		{

			body.getRow().add(createRow(view));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(View view) throws OfxAuthoringException
	{		
		Row row = new Row();
		
		String layerName;
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Lang l = StatusXpath.getLang(view.getLayer().getLangs(), langs[0]);
			layerName = l.getTranslation();
		}
		catch (ExlpXpathNotFoundException e){throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e){throw new OfxAuthoringException(e.getMessage());}
		
		
		row.getCell().add(OfxCellFactory.createParagraphCell(view.getNr()));
		row.getCell().add(OfxCellFactory.createParagraphCell(layerName));
//		row.getCell().add(OfxCellFactory.createParagraphCell("a"));

		return row;
	}
}