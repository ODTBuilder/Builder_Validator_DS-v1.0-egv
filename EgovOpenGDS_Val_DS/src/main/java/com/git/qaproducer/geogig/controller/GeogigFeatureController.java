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
import com.git.gdsbuilder.geogig.type.GeogigFeatureAttribute;
import com.git.gdsbuilder.geogig.type.GeogigFeatureDiff;
import com.git.gdsbuilder.geogig.type.GeogigFeatureRevert;
import com.git.gdsbuilder.geogig.type.GeogigFeatureSimpleLog;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.geogig.service.GeogigFeatureService;
import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.domain.User.EnUserType;

/**
 * @author GIT
 *
 */
@Controller
@RequestMapping("/geogig")
public class GeogigFeatureController extends AbstractController {

	@Autowired
	@Qualifier("featureService")
	GeogigFeatureService featureService;

	@RequestMapping(value = "/featureAttribute.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigFeatureAttribute featureBlame(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "commitId", required = false) String commitId) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return featureService.featureAttribute(geoserverManager, repoName, path, commitId);
	}

	@RequestMapping(value = "/featureLog.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigFeatureSimpleLog featureLog(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "until", required = false) String until,
			@RequestParam(value = "limit", required = false) int limit,
			@RequestParam(value = "index", required = false) int index) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return featureService.featureLog(geoserverManager, repoName, path, limit, until, index);
	}

	@RequestMapping(value = "/diff.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigDiff diff(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "newCommitId", required = false) String newCommitId,
			@RequestParam(value = "oldCommitId", required = false) String oldCommitId) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return featureService.diff(geoserverManager, repoName, path, newCommitId, oldCommitId);
	}

	@RequestMapping(value = "/featureDiff.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigFeatureDiff featureDiff(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "newCommitId", required = false) String newCommitId,
			@RequestParam(value = "oldCommitId", required = false) String oldCommitId) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return featureService.featureDiff(geoserverManager, repoName, path, newCommitId, oldCommitId);
	}

	@RequestMapping(value = "/featureRevert.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigFeatureRevert featureRevert(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "oldCommitId", required = false) String oldCommitId,
			@RequestParam(value = "newCommitId", required = false) String newCommitId,
			@RequestParam(value = "commitMessage", required = false) String commitMessage,
			@RequestParam(value = "mergeMessage", required = false) String mergeMessage) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return featureService.featureRevert(geoserverManager, repoName, path, oldCommitId, newCommitId, commitMessage,
				mergeMessage, loginUser);
	}
}
