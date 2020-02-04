package org.geojsf.interfaces.model.core;

import java.io.Serializable;
import java.util.List;

import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfLayer<L extends JeeslLang, D extends JeeslDescription,
							CATEGORY extends GeoJsfCategory<L,D,?>,
							SERVICE extends GeoJsfService<L,D,?>,
							VP extends GeoJsfViewPort,
							DS extends GeoJsfDataSource<L,D,?>,
							SLD extends GeoJsfSld<L,D,?,?,?>>
			extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,EjbWithCode,EjbWithPositionVisibleParent,
					EjbWithLang<L>,EjbWithDescription<D>
{
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	SERVICE getService();
	void setService(SERVICE service);

	VP getViewPort();
	void setViewPort(VP viewPort);
	
	boolean isTemporalLayer();
	void setTemporalLayer(boolean temporalLayer);
	
	Boolean isSqlLayer();
	Boolean getSqlLayer();
	void setSqlLayer(Boolean sqlLayer);
	
	SLD getSld();
	void setSld(SLD sld);
	
	List<DS> getSources();
	void setSources(List<DS> sources);
	
	String getRemark();
	void setRemark(String remark);
}