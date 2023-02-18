package org.geojsf.interfaces.controller.web;

import java.io.Serializable;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.util.with.EjbWithSld;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;

public interface GeoSldTimeSeriesCallback <SLD extends GeoJsfSld<?,?,?,?,?,?,?>>  extends Serializable
{
	<W extends EjbWithSld<SLD>> SLD fcSld(W entity) throws JeeslConstraintViolationException, JeeslLockingException;
}