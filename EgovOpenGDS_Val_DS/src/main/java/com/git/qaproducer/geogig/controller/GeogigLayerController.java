/**
 * 
 */
package com.git.qaproducer.geogig.controller;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.gdsbuilder.geogig.type.GeogigDiff;
import com.git.gdsbuilder.geogig.type.GeogigLayerSimpleLog;
import com.git.gdsbuilder.geogig.type.GeogigRemove;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.geogig.service.GeogigLayerService;
import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.domain.User.EnUserType;

/**
 * @author GIT
 *
 */
@Controller
@RequestMapping("/geogig")
public class GeogigLayerController extends AbstractController {

	@Autowired
	@Qualifier("layerService")
	GeogigLayerService layerService;

	@RequestMapping(value = "/logLayer.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigLayerSimpleLog logLayer(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "until", required = false) String until) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return layerService.logLayer(geoserverManager, repoName, path, limit, until);
	}

	@RequestMapping(value = "/diffLayer.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigDiff diffLayer(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "newIndex", required = false) int newIndex,
			@RequestParam(value = "oldIndex", required = false) int oldIndex,
			@RequestParam(value = "layerName", required = false) String layerName) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return layerService.diffLayer(geoserverManager, repoName, newIndex, oldIndex, layerName);
	}

	@RequestMapping(value = "/diffLayerById.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigDiff diffLayerById(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "new", required = false) String newId,
			@RequestParam(value = "old", required = false) String oldId,
			@RequestParam(value = "layerName", required = false) String layerName) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return layerService.diffLayerById(geoserverManager, repoName, newId, oldId, layerName);
	}

	@RequestMapping(value = "/removeLayer.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigRemove removeLayer(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "transactionId", required = false) String transactionId,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "recursive", required = false) boolean recursive) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return layerService.removeLayer(geoserverManager, repoName, transactionId, path, recursive);
	}
}
