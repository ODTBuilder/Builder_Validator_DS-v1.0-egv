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

package com.git.qaproducer.upload.service;

import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.git.gdsbuilder.fileread.FileMeta;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.user.domain.User;

public interface FileUploadService {

	public LinkedList<FileMeta> filesUpload(DTGeoserverManager dtGeoManager, User loginUser,
			MultipartHttpServletRequest request, HttpServletResponse response) throws Throwable;
}
