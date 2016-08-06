package org.geojsf.web.rest.monitoring;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.web.rest.AbstractUtilsRest;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.geojsf.factory.ejb.monitoring.EjbStationFactory;
import org.geojsf.interfaces.model.monitoring.GeoStation;
import org.geojsf.interfaces.model.monitoring.GeoStationCapability;
import org.geojsf.interfaces.rest.monitoring.station.GeoJsfMonitoringStationRestExport;
import org.geojsf.interfaces.rest.monitoring.station.GeoJsfMonitoringStationRestImport;
import org.geojsf.model.xml.monitoring.Station;
import org.geojsf.model.xml.monitoring.Stations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfMonitoringStationRest <L extends UtilsLang,D extends UtilsDescription, STATION extends GeoStation<L,D,STATION,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,CAP,CAPT,CAPS>, CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
	extends AbstractUtilsRest<L,D>
	implements GeoJsfMonitoringStationRestExport,GeoJsfMonitoringStationRestImport
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMonitoringStationRest.class);

	private final Class<STATION> cStation;
	@SuppressWarnings("unused")
	private final Class<CAP> cCap;
	private final Class<CAPT> cCapT;
	private final Class<CAPS> cCapS;
	
	private EjbStationFactory<L,D,STATION,CAP,CAPT,CAPS> efStation;

	public GeoJsfMonitoringStationRest(UtilsFacade fUtils, final String[] defaultLangs, final Class<L> cL, final Class<D> cD,final Class<STATION> cStation,final Class<CAP> cCap,final Class<CAPT> cCapT,final Class<CAPS> cCapS)
	{
		super(fUtils,defaultLangs,cL,cD);
		
		this.cStation=cStation;
		this.cCap=cCap;
		this.cCapT=cCapT;
		this.cCapS=cCapS;
		
		efStation = EjbStationFactory.factory(cL,cD,cStation);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription, STATION extends GeoStation<L,D,STATION,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,CAP,CAPT,CAPS>, CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
		GeoJsfMonitoringStationRest<L,D,STATION,CAP,CAPT,CAPS>
		factory(UtilsFacade fGeoMonitoring, final String[] defaultLangs, final Class<L> cL, final Class<D> cD,final Class<STATION> cStation,final Class<CAP> cCap,final Class<CAPT> cCapT,final Class<CAPS> cCapS)
	{
		return new GeoJsfMonitoringStationRest<L,D,STATION,CAP,CAPT,CAPS>(fGeoMonitoring,defaultLangs,cL,cD,cStation,cCap,cCapT,cCapS);
	}
	
	//Import
	@Override public DataUpdate importGeoJsfMonitoringCapabilityTypes(Aht types){return importStatus(cCapT,null,types);}
	@Override public DataUpdate importGeoJsfMonitoringCapabilityStatus(Aht statuses){return importStatus(cCapS,null,statuses);}
	
	@Override public DataUpdate importGeoJsfMonitoringStations(Stations stations)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cStation.getName(),"DB Import"));
		for(Station station : stations.getStation())
		{
			STATION ejb;
			try
			{
				ejb = fUtils.fByCode(cStation,station.getCode());
				dut.success();
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					List<CAP> capabilities = new ArrayList<CAP>();
/*					if(station.isSetCapabilities() && station.getCapabilities().isSetCapability())
					{
						for(Capability c : station.getCapabilities().getCapability())
						{
						//	capabilities.add(fGeoMonitoring.fByCode(cCapT,c.getCode()));
						}
					}
*/					
					ejb = efStation.build(station,capabilities);
					ejb = fUtils.persist(ejb);
					efLang.persistMissingLangs(fUtils,defaultLangs,ejb);
					efDescription.persistMissingLangs(fUtils,defaultLangs,ejb);
					dut.success();
				}
				catch (UtilsConstraintViolationException e1) {dut.fail(e1,true);}
	//			catch (UtilsNotFoundException e1) {dut.fail(e1,true);}
			}
		}
		return dut.toDataUpdate();
	}
	
	
	//Export
	@Override public Aht exportGeoJsfMonitoringCapabilityTypes(){return exportStatus(cCapT);}
	@Override public Aht exportGeoJsfMonitoringCapabilityStatus(){return exportStatus(cCapS);}
	
	@Override
	public Stations exportGeoJsfMonitoringStations()
	{
		// TODO Auto-generated method stub
		return null;
	}


}