package org.geojsf.web.rest;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.facade.UtilsIdFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.geojsf.interfaces.rest.monitoring.station.GeoJsfMonitoringStationExportRest;
import org.geojsf.interfaces.rest.monitoring.station.GeoJsfMonitoringStationImportRest;
import org.geojsf.model.xml.monitoring.Stations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfMonitoringStationRest <L extends UtilsLang,D extends UtilsDescription, CAPABILITY extends UtilsStatus<CAPABILITY,L,D>>
	implements GeoJsfMonitoringStationExportRest,GeoJsfMonitoringStationImportRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMonitoringStationRest.class);

	private UtilsFacade fGeoMonitoring;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CAPABILITY> cCap;
	
	private String groupCapabilities;
	public void setGroupCapabilities(String groupCapabilities){this.groupCapabilities = groupCapabilities;}

	public GeoJsfMonitoringStationRest(UtilsFacade fGeoMonitoring, final Class<L> cL, final Class<D> cD,final Class<CAPABILITY> cCap)
	{
		this.fGeoMonitoring=fGeoMonitoring;
		this.cL=cL;
		this.cD=cD;
		this.cCap=cCap;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,CAPABILITY extends UtilsStatus<CAPABILITY,L,D>>
		GeoJsfMonitoringStationRest<L,D,CAPABILITY>
		factory(UtilsFacade fGeoMonitoring, final Class<L> cL, final Class<D> cD,final Class<CAPABILITY> cCap)
	{
		return new GeoJsfMonitoringStationRest<L,D,CAPABILITY>(fGeoMonitoring,cL,cD,cCap);
	}
	
	//Import
	private <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> 
		DataUpdate importStatus(Class<S> cS, Class<P> cP, Aht status)
	{
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(cS, cL, cD));
        asdi.setFacade(fGeoMonitoring);
        DataUpdate dataUpdate = asdi.iuStatus(status.getStatus(), cS, cL, cP);
        asdi.deleteUnusedStatus(cS, cL, cD);
        return dataUpdate;
	}
	
	@Override public DataUpdate importGeoJsfMonitoringCapabilities(Aht status){return importStatus(cCap,null,status);}

	
	//Export
	@Override
	public Aht exportGeoJsfMonitoringTypes()
	{
		XmlStatusFactory f = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport, "").getStatus());
		
		Aht xml = new Aht();
		for(CAPABILITY cap : fGeoMonitoring.all(cCap))
		{
			Status status = f.build(cap);
			status.setGroup(groupCapabilities);
			xml.getStatus().add(status);
		}
		return xml;
	}

	@Override
	public Stations exportGeoJsfMonitoringStations()
	{
		// TODO Auto-generated method stub
		return null;
	}

	}