/**
 * 
 */
package com.git.qaproducer.geogig.service.impl;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geogig.GeogigCommandException;
import com.git.gdsbuilder.geogig.command.ResponseType;
import com.git.gdsbuilder.geogig.command.geoserver.GeoserverDataStore;
import com.git.gdsbuilder.geogig.command.geoserver.ListGeoserverDataStore;
import com.git.gdsbuilder.geogig.command.geoserver.ListGeoserverLayer;
import com.git.gdsbuilder.geogig.command.geoserver.ListGeoserverLayer.ListParam;
import com.git.gdsbuilder.geogig.command.geoserver.ListGeoserverWorkSpace;
import com.git.gdsbuilder.geogig.command.geoserver.PublishGeoserverLayer;
import com.git.gdsbuilder.geogig.command.object.CatObject;
import com.git.gdsbuilder.geogig.command.repository.LsTreeRepository;
import com.git.gdsbuilder.geogig.type.GeogigCat;
import com.git.gdsbuilder.geogig.type.GeogigCat.CatAttribute;
import com.git.gdsbuilder.geogig.type.GeogigCat.FeatureType;
import com.git.gdsbuilder.geogig.type.GeogigCommandResponse;
import com.git.gdsbuilder.geogig.type.GeogigGeoserverDataStore;
import com.git.gdsbuilder.geogig.type.GeogigGeoserverDataStore.ConnectionParameters;
import com.git.gdsbuilder.geogig.type.GeogigGeoserverDataStore.Entry;
import com.git.gdsbuilder.geogig.type.GeogigGeoserverDataStoreList;
import com.git.gdsbuilder.geogig.type.GeogigGeoserverDataStoreList.DataStore;
import com.git.gdsbuilder.geogig.type.GeogigGeoserverLayerList;
import com.git.gdsbuilder.geogig.type.GeogigGeoserverWorkSpaceList;
import com.git.gdsbuilder.geogig.type.GeogigGeoserverWorkSpaceList.Workspace;
import com.git.gdsbuilder.geogig.type.GeogigRevisionTree;
import com.git.gdsbuilder.geogig.type.GeogigRevisionTree.Node;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.geogig.service.GeogigGeoserverService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @author GIT
 *
 */
@Service("gsService")
public class GeogigGeoserverServiceImpl extends EgovAbstractServiceImpl implements GeogigGeoserverService {

	@Override
	public JSONObject getDataStoreList(DTGeoserverManager geoserverManager, String repoName, String branchName) {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		JSONObject dsListObj = new JSONObject();

		// get workspaces
		ListGeoserverWorkSpace listGws = new ListGeoserverWorkSpace();
		GeogigGeoserverWorkSpaceList geogigGws = null;
		try {
			geogigGws = listGws.executeCommand(url, user, pw, ResponseType.XML);
			List<Workspace> workspaces = geogigGws.getWorkspaces();
			if (workspaces != null) {
				for (Workspace workspace : workspaces) {
					String wrName = workspace.getName();
					// get datastores
					ListGeoserverDataStore listGds = new ListGeoserverDataStore();
					GeogigGeoserverDataStoreList geogigGdsList = listGds.executeCommand(url, user, pw, wrName,
							ResponseType.XML);
					List<DataStore> dataStores = geogigGdsList.getDataStores();
					if (dataStores != null) {
						JSONArray dsArr = new JSONArray();
						String ws = null;
						for (DataStore dataStore : dataStores) {
							String dsName = dataStore.getName();
							GeoserverDataStore gds = new GeoserverDataStore();
							GeogigGeoserverDataStore geogigGds = gds.executeCommand(url, user, pw, wrName, dsName,
									ResponseType.XML);
							String type = geogigGds.getType();

							// check geogig type
							if (type.equalsIgnoreCase("GeoGIG")) {
								ConnectionParameters connParam = geogigGds.getConnetParams();
								List<Entry> entryList = connParam.getEntryList();
								// check geogig ws
								boolean isWs = false;
								for (Entry entry : entryList) {
									String key = entry.getKey();
									String value = entry.getXmlValue();
									if (key.equalsIgnoreCase("geogig_repository")) {
										String repoValue = value.replace("geoserver://", "");
										if (repoValue.equalsIgnoreCase(repoName)) {
											ws = wrName;
											isWs = true;
										}
									}
								}
								// check geogig ds
								if (isWs) {
									for (Entry entry : entryList) {
										String key = entry.getKey();
										String value = entry.getXmlValue();
										if (key.equalsIgnoreCase("branch")) {
											if (value.equalsIgnoreCase(branchName)) {
												dsArr.add(dsName);
											}
										}
									}
								}
							}
						}
						if (dsArr.size() > 0) {
							dsListObj.put(ws, dsArr);
						}
					}
				}
			}
		} catch (GeogigCommandException e) {
			// TODO: handle exception
		}
		return dsListObj;
	}

	@Override
	public GeogigCommandResponse publishGeogigLayer(DTGeoserverManager geoserverManager, String workspace,
			String datastore, String layer, String repoName, String branchName) {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		// get crs
		String crs = null;
		LsTreeRepository lsTreeRepos = new LsTreeRepository();
		GeogigRevisionTree geogigLsTree = lsTreeRepos.executeCommand(url, user, pw, repoName, branchName, true);
		List<Node> nodeList = geogigLsTree.getNodes();
		for (Node node : nodeList) {
			if (layer.equalsIgnoreCase(node.getPath())) {
				String metadataId = node.getMetadataId();
				CatObject catObj = new CatObject();
				GeogigCat geogigCat = catObj.executeCommand(url, user, pw, repoName, metadataId);
				FeatureType featureType = geogigCat.getFeaturetype();
				List<CatAttribute> attrList = featureType.getAttribute();
				for (CatAttribute attr : attrList) {
					String geogigCrs = attr.getCrs();
					if (geogigCrs != null) {
						crs = geogigCrs;
						break;
					}
				}
			}
		}
		PublishGeoserverLayer publish = new PublishGeoserverLayer();
		GeogigCommandResponse response = new GeogigCommandResponse();
		try {
			boolean isSuccess = publish.executeCommand(url, user, pw, workspace, datastore, layer, crs, true);
			if (isSuccess) {
				response.setSuccess("true");
			}
		} catch (GeogigCommandException e) {
			response.setSuccess("false");
			response.setError(e.getMessage());
		}
		return response;
	}

	@Override
	public JSONArray listGeoserverLayer(DTGeoserverManager geoserverManager, String workspace, String datastore) {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		JSONArray layerArr = new JSONArray();
		try {
			// all layer
			ListGeoserverLayer listAllLayer = new ListGeoserverLayer();
			GeogigGeoserverLayerList allGsLayerList = listAllLayer.executeCommand(url, user, pw, workspace, datastore,
					ResponseType.XML, ListParam.ALL);
			List<String> featureTypeList = allGsLayerList.getFeatureTypeNames();
			if (featureTypeList != null) {
				// unpublished layer
				GeogigGeoserverLayerList listAvailableLayer = listAllLayer.executeCommand(url, user, pw, workspace,
						datastore, ResponseType.XML, ListParam.AVAILABLE);
				List<String> availableGsLayerList = listAvailableLayer.getFeatureTypeNames();
				for (String featureType : featureTypeList) {
					JSONObject layerObj = new JSONObject();
					layerObj.put("layerName", featureType);
					boolean isPublished = true;
					if (availableGsLayerList != null) {
						for (String availableFeatureType : availableGsLayerList) {
							if (featureType.equals(availableFeatureType)) {
								isPublished = false;
							}
						}
					}
					layerObj.put("published", isPublished);
					layerArr.add(layerObj);
				}
			}
		} catch (GeogigCommandException e) {
			// TODO: handle exception
		}
		return layerArr;
	}
}
