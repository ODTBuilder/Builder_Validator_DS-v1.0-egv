package com.git.qaproducer.geoserver.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.git.gdsbuilder.geoserver.DTGeoserverManager;

public interface GeoserverLayerProxyService {
	public void requestGetMap(DTGeoserverManager dtGeoManager, String workspace, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	public void requestGetFeature(DTGeoserverManager dtGeoManager, String workspace, HttpServletRequest request,
			HttpServletResponse response) throws IOException;

	public void requestGetFeatureInfo(DTGeoserverManager dtGeoManager, String workspace, HttpServletRequest request,
			HttpServletResponse response) throws IOException;

	public void requestWMSGetLegendGraphic(DTGeoserverManager dtGeoManager, String workspace,
			HttpServletRequest request, HttpServletResponse response) throws IOException;

	public String requestGeoserverInfo(DTGeoserverManager dtGeoManager, HttpServletRequest request,
			HttpServletResponse response) throws IOException;

}
