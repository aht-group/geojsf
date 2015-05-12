package org.geojsf.web.rest;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Capability;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.geojsf.factory.ejb.monitoring.EjbStationFactory;
import org.geojsf.interfaces.model.monitoring.GeoStation;
import org.geojsf.interfaces.rest.monitoring.station.GeoJsfMonitoringStationExportRest;
import org.geojsf.interfaces.rest.monitoring.station.GeoJsfMonitoringStationImportRest;
import org.geojsf.model.xml.monitoring.Station;
import org.geojsf.model.xml.monitoring.Stations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfMonitoringStationRest <L extends UtilsLang,D extends UtilsDescription, STATION extends GeoStation<L,D,CAP>, CAP extends UtilsStatus<CAP,L,D>>
	implements GeoJsfMonitoringStationExportRest,GeoJsfMonitoringStationImportRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMonitoringStationRest.class);

	private UtilsFacade fGeoMonitoring;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<CAP> cCap;
	private final Class<STATION> cStation;
	
	private final String[] defaultLangs;
	private EjbLangFactory<L> efLang;
    private EjbDescriptionFactory<D> efDescription;
	private EjbStationFactory<L,D,CAP,STATION> efStation;
	
	
	private String groupCapabilities;
	public void setGroupCapabilities(String groupCapabilities){this.groupCapabilities = groupCapabilities;}

	public GeoJsfMonitoringStationRest(UtilsFacade fGeoMonitoring, final String[] defaultLangs, final Class<L> cL, final Class<D> cD,final Class<STATION> cStation,final Class<CAP> cCap)
	{
		this.fGeoMonitoring=fGeoMonitoring;
		this.defaultLangs=defaultLangs;
		this.cL=cL;
		this.cD=cD;
		this.cCap=cCap;
		this.cStation=cStation;
		
        efLang = EjbLangFactory.createFactory(cL);
        efDescription = EjbDescriptionFactory.createFactory(cD);
		efStation = EjbStationFactory.factory(cL,cD,cStation);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,CAP extends UtilsStatus<CAP,L,D>,STATION extends GeoStation<L,D,CAP>>
		GeoJsfMonitoringStationRest<L,D,STATION,CAP>
		factory(UtilsFacade fGeoMonitoring, final String[] defaultLangs, final Class<L> cL, final Class<D> cD,final Class<STATION> cStation,final Class<CAP> cCap)
	{
		return new GeoJsfMonitoringStationRest<L,D,STATION,CAP>(fGeoMonitoring,defaultLangs,cL,cD,cStation,cCap);
	}
	
	//Import
	private <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> 
		DataUpdate importStatus(Class<S> cS, Class<P> cP, Aht status)
	{
		AhtStatusDbInit<S,L,D> asdi = new AhtStatusDbInit<S,L,D>();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(cS, cL, cD));
        asdi.setFacade(fGeoMonitoring);
        DataUpdate dataUpdate = asdi.iuStatus(status.getStatus(), cS, cL, cP);
        asdi.deleteUnusedStatus(cS, cL, cD);
        return dataUpdate;
	}
	
	@Override public DataUpdate importGeoJsfMonitoringCapabilities(Aht status){return importStatus(cCap,null,status);}

	@Override
	public DataUpdate importGeoJsfMonitoringStations(Stations stations)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cStation.getName(),"DB Import"));
		for(Station station : stations.getStation())
		{
			STATION ejb;
			try
			{
				ejb = fGeoMonitoring.fByCode(cStation,station.getCode());
				dut.success();
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					List<CAP> capabilities = new ArrayList<CAP>();
					if(station.isSetCapabilities() && station.getCapabilities().isSetCapability())
					{
						for(Capability c : station.getCapabilities().getCapability())
						{
							capabilities.add(fGeoMonitoring.fByCode(cCap,c.getCode()));
						}
					}
					
					ejb = efStation.build(station,capabilities);
					ejb = fGeoMonitoring.persist(ejb);
					efLang.persistMissingLangs(fGeoMonitoring,defaultLangs,ejb);
					efDescription.persistMissingLangs(fGeoMonitoring,defaultLangs,ejb);
					dut.success();
				}
				catch (UtilsConstraintViolationException e1) {dut.fail(e1,true);}
				catch (UtilsNotFoundException e1) {dut.fail(e1,true);}
			}
		}
		return dut.toDataUpdate();
	}
	
	
	//Export
	@Override public Aht exportGeoJsfMonitoringCapabilityTypes(){return exportStatus(cCap,groupCapabilities);}
	@Override public Aht exportGeoJsfMonitoringCapabilityStatus(){return exportStatus(cCap,groupCapabilities);}
	
	private <S extends UtilsStatus<S,L,D>> Aht exportStatus(Class<S> c, String group)
	{
		XmlStatusFactory f = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport, "").getStatus());
		
		Aht xml = new Aht();
		for(S s : fGeoMonitoring.all(c))
		{
			Status status = f.build(s);
			status.setGroup(group);
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