package org.geojsf.factory.xml.monitoring;

import java.io.Serializable;

import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Type;

import org.geojsf.model.xml.monitoring.Capability;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCapabilityFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlCapabilityFactory.class);
	public static final long serialVersionUID=1;
	
	public static Capability build(String type, String status)
	{
		Type xType = XmlTypeFactory.create(type);
		Status xStatus = XmlStatusFactory.create(status);
		return build(xType,xStatus);
	}
	
	public static Capability build(Type type, Status status)
	{
		Capability xml = new Capability();
		xml.setType(type);
		xml.setStatus(status);
		return xml;
	}
}
