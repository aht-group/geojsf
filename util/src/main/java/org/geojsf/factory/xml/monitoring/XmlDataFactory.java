package org.geojsf.factory.xml.monitoring;

import java.io.Serializable;
import java.util.Date;

import org.exlp.util.system.DateUtil;
import org.geojsf.model.xml.monitoring.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDataFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataFactory.class);
	public static final long serialVersionUID=1;
	
	public static Data build(Date record)
	{
		Data xml = new Data();
		xml.setRecord(DateUtil.toXmlGc(record));
		return xml;
	}
}
