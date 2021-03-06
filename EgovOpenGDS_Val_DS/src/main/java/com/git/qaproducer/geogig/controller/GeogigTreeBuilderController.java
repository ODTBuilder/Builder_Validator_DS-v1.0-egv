/**
 * 
 */
package com.git.qaproducer.geogig.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.gdsbuilder.geogig.tree.GeogigRemoteRepositoryTree.EnGeogigRemoteRepositoryTreeType;
import com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree.EnGeogigRepositoryTreeType;
import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.geogig.service.GeogigTreeBuilderService;
import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.domain.User.EnUserType;

/**
 * @author GIT
 *
 */
@Controller
@RequestMapping("/geogig")
public class GeogigTreeBuilderController extends AbstractController {

	@Autowired
	@Qualifier("treeService")
	GeogigTreeBuilderService treeService;

	@RequestMapping(value = "/getWorkingTree.ajax")
	@ResponseBody
	public JSONArray getWorkingTree(HttpServletRequest request,
			@RequestParam(value = "node", required = false) String node,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "transactionId", required = false) String transactionId) {

		EnGeogigRepositoryTreeType enType = null;

		if (type.equals(EnGeogigRepositoryTreeType.SERVER.getType())) {
			enType = EnGeogigRepositoryTreeType.SERVER;
		} else if (type.equals(EnGeogigRepositoryTreeType.REPOSITORY.getType())) {
			enType = EnGeogigRepositoryTreeType.REPOSITORY;
		} else if (type.equals(EnGeogigRepositoryTreeType.BRANCH.getType())) {
			enType = EnGeogigRepositoryTreeType.BRANCH;
		} else if (type.equals(EnGeogigRepositoryTreeType.LAYER.getType())) {
			enType = EnGeogigRepositoryTreeType.LAYER;
		} else {
			enType = EnGeogigRepositoryTreeType.UNKNOWN;
		}
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManagerList geoserverManagers = super.getGeoserverManagersToSession(request, loginUser);
		return treeService.getWorkingTree(geoserverManagers, serverName, enType, node, transactionId);
	}

	@RequestMapping(value = "/getRemoteRepoTree.ajax")
	@ResponseBody
	public JSONArray getRemoteRepoTree(HttpServletRequest request,
			@RequestParam(value = "node", required = false) String node,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "local", required = false) String local,
			@RequestParam(value = "fetch", required = false) boolean fetch) {

		EnGeogigRemoteRepositoryTreeType enType = null;

		if (type.equals(EnGeogigRemoteRepositoryTreeType.REMOTEREPOSITORY.getType())) {
			enType = EnGeogigRemoteRepositoryTreeType.REMOTEREPOSITORY;
		} else if (type.equals(EnGeogigRemoteRepositoryTreeType.REMOTEBRANCH.getType())) {
			enType = EnGeogigRemoteRepositoryTreeType.REMOTEBRANCH;
		} else {
			enType = EnGeogigRemoteRepositoryTreeType.UNKNOWN;
		}

		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManagerList geoserverManagers = super.getGeoserverManagersToSession(request, loginUser);
		return treeService.getRemoteRepoTree(geoserverManagers, serverName, enType, node, local, fetch);
	}
}
