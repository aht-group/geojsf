package org.geojsf.web.rest.monitoring;

import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.builder.GeoMonitoringFactoryBuilder;
import org.geojsf.factory.ejb.monitoring.EjbStationFactory;
import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;
import org.geojsf.interfaces.model.obervation.station.GeoStationCode;
import org.geojsf.interfaces.rest.monitoring.station.GeoJsfMonitoringStationRestExport;
import org.geojsf.interfaces.rest.monitoring.station.GeoJsfMonitoringStationRestImport;
import org.geojsf.model.xml.monitoring.Station;
import org.geojsf.model.xml.monitoring.Stations;
import org.jeesl.controller.monitor.DataUpdateTracker;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.rest.AbstractUtilsRest;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class GeoJsfMonitoringStationRest <L extends UtilsLang,D extends UtilsDescription,
										STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
										SCHEME extends UtilsStatus<SCHEME,L,D>,
										CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, 
										CAPT extends UtilsStatus<CAPT,L,D>,
										CAPS extends UtilsStatus<CAPS,L,D>>
	extends AbstractUtilsRest<L,D>
	implements GeoJsfMonitoringStationRestExport,GeoJsfMonitoringStationRestImport
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMonitoringStationRest.class);

	private final GeoMonitoringFactoryBuilder<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS> fbMonitoring;
	
	private final Class<CAPT> cCapT;
	private final Class<CAPS> cCapS;
	
	private EjbStationFactory<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS> efStation;

	public GeoJsfMonitoringStationRest(JeeslFacade fUtils, final String[] defaultLangs,
			GeoMonitoringFactoryBuilder<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS> fbMonitoring,
			final Class<CAPT> cCapT,final Class<CAPS> cCapS)
	{
		super(fUtils,defaultLangs,fbMonitoring.getClassL(),fbMonitoring.getClassD());
		this.fbMonitoring=fbMonitoring;
		
		this.cCapT=cCapT;
		this.cCapS=cCapS;
		
		efStation = fbMonitoring.station();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription, STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, TYPE extends UtilsStatus<TYPE,L,D>, SUBTYPE extends UtilsStatus<SUBTYPE,L,D>, SCHEME extends UtilsStatus<SCHEME,L,D>, CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>, CAPT extends UtilsStatus<CAPT,L,D>,CAPS extends UtilsStatus<CAPS,L,D>>
		GeoJsfMonitoringStationRest<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>
		factory(JeeslFacade fGeoMonitoring, final String[] defaultLangs,
				GeoMonitoringFactoryBuilder<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS> fbMonitoring,
				final Class<STATION> cStation,final Class<CAP> cCap,final Class<CAPT> cCapT,final Class<CAPS> cCapS)
	{
		return new GeoJsfMonitoringStationRest<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>(fGeoMonitoring,defaultLangs,fbMonitoring
					,cCapT,cCapS);
	}
	
	//Import
	@Override public DataUpdate importGeoJsfMonitoringCapabilityTypes(Aht types){return importStatus(cCapT,null,types);}
	@Override public DataUpdate importGeoJsfMonitoringCapabilityStatus(Aht statuses){return importStatus(cCapS,null,statuses);}
	
	@Override public DataUpdate importGeoJsfMonitoringStations(Stations stations)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(fbMonitoring.getClassStation().getName(),"DB Import"));
		for(Station station : stations.getStation())
		{
			STATION ejb;
			try
			{
				ejb = fUtils.fByCode(fbMonitoring.getClassStation(),station.getCode());
				dut.success();
			}
			catch (JeeslNotFoundException e)
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
				catch (JeeslConstraintViolationException e1) {dut.fail(e1,true);}
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