package org.geojsf.web.rest;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractGeoJsfRest <L extends UtilsLang,D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfRest.class);

	protected UtilsFacade fUtils;
	
	protected final Class<L> cL;
	protected final Class<D> cD;
	
	protected final String[] defaultLangs;
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;

	public AbstractGeoJsfRest(UtilsFacade fUtils, final String[] defaultLangs, final Class<L> cL, final Class<D> cD)
	{
		this.fUtils=fUtils;
		this.defaultLangs=defaultLangs;
		this.cL=cL;
		this.cD=cD;
		
        efLang = EjbLangFactory.createFactory(cL);
        efDescription = EjbDescriptionFactory.createFactory(cD);
	}
	
	protected <S extends UtilsStatus<S,L,D>> Aht exportStatus(Class<S> c, String group)
	{
		XmlStatusFactory f = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport, "").getStatus());
		
		Aht xml = new Aht();
		for(S s : fUtils.all(c))
		{
			Status status = f.build(s);
			status.setGroup(group);
			xml.getStatus().add(status);
		}
		return xml;
	}
}