/**
 * 
 */
package com.git.qaproducer.geogig.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geogig.GeogigCommandException;
import com.git.gdsbuilder.geogig.GeogigExceptionStatus;
import com.git.gdsbuilder.geogig.command.object.CatObject;
import com.git.gdsbuilder.geogig.command.repository.LsTreeRepository;
import com.git.gdsbuilder.geogig.type.GeogigCat;
import com.git.gdsbuilder.geogig.type.GeogigCat.CatAttribute;
import com.git.gdsbuilder.geogig.type.GeogigCat.Commit;
import com.git.gdsbuilder.geogig.type.GeogigCat.Feature;
import com.git.gdsbuilder.geogig.type.GeogigCat.FeatureType;
import com.git.gdsbuilder.geogig.type.GeogigCat.Subtree;
import com.git.gdsbuilder.geogig.type.GeogigFeatureAttribute;
import com.git.gdsbuilder.geogig.type.GeogigFeatureAttribute.Attribute;
import com.git.gdsbuilder.geogig.type.GeogigRevisionTree;
import com.git.gdsbuilder.geogig.type.GeogigRevisionTree.Node;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @author GIT
 *
 */
@Service("objectService")
public class GeogigObjectServiceImpl extends EgovAbstractServiceImpl implements GeogigObjectService {

	@Override
	public GeogigCat catObject(DTGeoserverManager geoserverManager, String repoName, String objectid)
			throws JAXBException {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		CatObject catObj = new CatObject();
		GeogigCat cat = null;
		try {
			cat = catObj.executeCommand(url, user, pw, repoName, objectid);
		} catch (GeogigCommandException e) {
			if (e.isXml()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(GeogigCat.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				cat = (GeogigCat) unmarshaller.unmarshal(new StringReader(e.getResponseBodyAsString()));
			} else {
				cat = new GeogigCat();
				cat.setError(e.getMessage());
				cat.setSuccess("false");
			}
			GeogigExceptionStatus geogigStatus = GeogigExceptionStatus.getStatus(cat.getError());
			cat.setError(geogigStatus.getStatus());
		}
		return cat;
	}

	@Override
	public GeogigFeatureAttribute catConflictFeatureObject(DTGeoserverManager geoserverManager, String repoName,
			String path, String commitId, String featureId) throws JAXBException {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		String[] ids = path.split("/");
		String layerName = ids[0];
		String featureName = ids[1];
		GeogigFeatureAttribute featureAtt = null;
		try {
			CatObject catObj = new CatObject();
			List<CatAttribute> attrTypes = null;
			GeogigCat catOurs = catObj.executeCommand(url, user, pw, repoName, commitId);
			Commit commit = catOurs.getCommit();
			if (commit != null) {
				String treeId = catOurs.getCommit().getTree();
				GeogigCat catTree = catObj.executeCommand(url, user, pw, repoName, treeId);
				List<Subtree> subTrees = catTree.getTree().getSubtree();
				for (Subtree subTree : subTrees) {
					if (subTree.getName().equals(layerName)) {
						String metaDataObjId = subTree.getMetadataid();
						GeogigCat catFeatureType = catObj.executeCommand(url, user, pw, repoName, metaDataObjId);
						attrTypes = catFeatureType.getFeaturetype().getAttribute();
					}
				}
				if (attrTypes != null) {
					featureAtt = new GeogigFeatureAttribute();
					GeogigCat catFeature = catObj.executeCommand(url, user, pw, repoName, featureId);
					Feature feature = catFeature.getFeature();
					List<CatAttribute> attrValues = feature.getAttribute();
					List<Attribute> attrList = new ArrayList<>();
					for (CatAttribute attrType : attrTypes) {
						String name = attrType.getName();
						String type = attrType.getType();
						Attribute attr = new Attribute();
						attr.setName(name);
						attr.setType(type);
						String crs = attrType.getCrs();
						if (attrType.getCrs() != null) {
							attr.setCrs(crs);
						}
						attrList.add(attr);
					}
					for (int i = 0; i < attrList.size(); i++) {
						Attribute attr = attrList.get(i);
						String value = attrValues.get(i).getValue();
						attr.setValue(value);
					}
					if (attrList.size() > 0) {
						featureAtt.setAttributes(attrList);
					}
					featureAtt.setLayerName(layerName);
					featureAtt.setFeatureId(featureName);
					featureAtt.setSuccess("true");
				}
			}
		} catch (GeogigCommandException e) {
			if (e.isXml()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(GeogigFeatureAttribute.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				featureAtt = (GeogigFeatureAttribute) unmarshaller
						.unmarshal(new StringReader(e.getResponseBodyAsString()));
			} else {
				featureAtt = new GeogigFeatureAttribute();
				featureAtt.setError(e.getMessage());
				featureAtt.setSuccess("false");
			}
			GeogigExceptionStatus geogigStatus = GeogigExceptionStatus.getStatus(featureAtt.getError());
			featureAtt.setError(geogigStatus.getStatus());
		}
		return featureAtt;
	}

	@Override
	public GeogigFeatureAttribute catFeatureObject(DTGeoserverManager geoserverManager, String repoName, String path,
			String objectid) throws JAXBException {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		String[] ids = path.split("/");
		String layerName = ids[0];
		String featureName = ids[1];
		GeogigFeatureAttribute featureAtt = null;
		try {
			CatObject catObj = new CatObject();
			GeogigCat geogigCat = catObj.executeCommand(url, user, pw, repoName, objectid);
			Feature feature = geogigCat.getFeature();
			List<CatAttribute> attrValues = feature.getAttribute();
			if (attrValues != null) {
				LsTreeRepository lsTreeRepos = new LsTreeRepository();
				GeogigRevisionTree geogigLsTree = lsTreeRepos.executeCommand(url, user, pw, repoName, path, true);
				List<Node> nodes = geogigLsTree.getNodes();
				for (Node node : nodes) {
					if (path.equalsIgnoreCase(node.getPath())) {
						featureAtt = new GeogigFeatureAttribute();
						String metaId = node.getMetadataId();
						GeogigCat geogigCatMeta = catObj.executeCommand(url, user, pw, repoName, metaId);
						FeatureType featureType = geogigCatMeta.getFeaturetype();
						List<CatAttribute> typeAttrList = featureType.getAttribute();
						List<Attribute> attrList = new ArrayList<>();
						for (CatAttribute typeAttr : typeAttrList) {
							String name = typeAttr.getName();
							String type = typeAttr.getType();
							Attribute attr = new Attribute();
							attr.setName(name);
							attr.setType(type);
							String crs = typeAttr.getCrs();
							if (typeAttr.getCrs() != null) {
								attr.setCrs(crs);
							}
							attrList.add(attr);
						}
						for (int i = 0; i < attrList.size(); i++) {
							Attribute attr = attrList.get(i);
							String value = attrValues.get(i).getValue();
							attr.setValue(value);
						}
						if (attrList.size() > 0) {
							featureAtt.setAttributes(attrList);
						}
						featureAtt.setLayerName(layerName);
						featureAtt.setFeatureId(featureName);
						featureAtt.setSuccess("true");
					}
				}
			}
		} catch (GeogigCommandException e) {
			if (e.isXml()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(GeogigFeatureAttribute.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				featureAtt = (GeogigFeatureAttribute) unmarshaller
						.unmarshal(new StringReader(e.getResponseBodyAsString()));
			} else {
				featureAtt = new GeogigFeatureAttribute();
				featureAtt.setError(e.getMessage());
				featureAtt.setSuccess("false");
			}
			GeogigExceptionStatus geogigStatus = GeogigExceptionStatus.getStatus(featureAtt.getError());
			featureAtt.setError(geogigStatus.getStatus());
		}
		return featureAtt;
	}
}
