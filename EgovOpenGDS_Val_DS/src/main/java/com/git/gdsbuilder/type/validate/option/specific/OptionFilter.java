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
package com.git.gdsbuilder.type.validate.option.specific;

import java.util.List;

/**
 * {@link com.git.gdsbuilder.type.validate.option.specific.AttributeMiss} 또는
 * {@link com.git.gdsbuilder.type.validate.option.specific.GraphicMiss} 수행 시
 * 사용되는 속성 필터
 * <p>
 * {@link com.git.gdsbuilder.type.validate.option.specific.OptionFilter}에 따라 해당
 * 레이어의 특정 속성 값을 가진 객체만 검수 수행. 레이어의 모든 객체에 적용됨.
 * 
 * @author DY.Oh
 *
 */
public class OptionFilter {

	/**
	 * 속성 필터를 적용할 레이어 ID
	 */
	String layerID;
	/**
	 * 속성 필터
	 */
	List<AttributeFilter> filter;

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public List<AttributeFilter> getFilter() {
		return filter;
	}

	public void setFilter(List<AttributeFilter> filter) {
		this.filter = filter;
	}

}
