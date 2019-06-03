/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.git.qaproducer.upload.controller;

import java.util.LinkedList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.git.gdsbuilder.fileread.FileMeta;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.upload.service.FileUploadService;
import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.domain.User.EnUserType;

/**
 * 파일 업로드와 관련된 요청을 수행한다.
 * 
 * @author SG.Lee
 * @Date 2017.04.11
 */
@Controller("fileUploadController")
@RequestMapping("/file")
public class FileUploadController extends AbstractController {

	@Resource(name = "fileUploadService")
	private FileUploadService fileService;

	/**
	 * @throws Throwable
	 *             파일업로드 @author SG.Lee @Date 2017. 4 @param request @param
	 *             response @return @throws Exception
	 *             LinkedList<FileMeta> @throws
	 */
	@RequestMapping(value = "/fileUpload.ajax", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> fileUploadRequest(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Throwable {

		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		if (loginUser == null) {
			response.sendError(600);
			throw new NullPointerException("로그인 세션이 존재하지 않습니다.");
		}
		String serverName = request.getParameter("serverName");
		DTGeoserverManager dtGeoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		files = fileService.filesUpload(dtGeoserverManager, loginUser, request, response);
		return files;
	}
}
