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
 * 특정 레이어의 객체 속성 값 검수 항목을 정의하는 클래스
 * 
 * @author DY.Oh
 *
 */
public class AttributeMiss {

	/**
	 * 검수 항목 이름
	 */
	String option;
	/**
	 * {@link com.git.gdsbuilder.type.validate.option.specific.OptionFilter}에 따라
	 * 해당 레이어의 특정 속성 값을 가진 객체만 검수 수행
	 */
	List<OptionFilter> filter;
	/**
	 * 검수 대상 레이어와
	 * {@link com.git.gdsbuilder.type.validate.option.specific.OptionRelation}에
	 * 저장된 타 레이어와의 위상관계 검수 대상 레이어의 검수 수행
	 */
	List<OptionRelation> retaion;
	/**
	 * {@link com.git.gdsbuilder.type.validate.option.specific.OptionFigure}에
	 * 저장된 레이어의 속성 key, value 정보에 따라 검수 대상 레이어의 검수 수행
	 */
	List<OptionFigure> figure;
	/**
	 * {@link com.git.gdsbuilder.type.validate.option.specific.OptionTolerance}에
	 * 저장된 허용 오차범위, 수치 조건 등의 수치 정보에 따라 검수 대상 레이어의 검수 수행
	 */
	List<OptionTolerance> tolerance;

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public List<OptionFilter> getFilter() {
		return filter;
	}

	public void setFilter(List<OptionFilter> filter) {
		this.filter = filter;
	}

	public List<OptionRelation> getRetaion() {
		return retaion;
	}

	public void setRetaion(List<OptionRelation> retaion) {
		this.retaion = retaion;
	}

	public List<OptionFigure> getFigure() {
		return figure;
	}

	public void setFigure(List<OptionFigure> figure) {
		this.figure = figure;
	}

	public List<OptionTolerance> getTolerance() {
		return tolerance;
	}

	public void setTolerance(List<OptionTolerance> tolerance) {
		this.tolerance = tolerance;
	}

	/**
	 * layerID에 해당하는
	 * {@link com.git.gdsbuilder.type.validate.option.specific.OptionFilter} 반환
	 * 
	 * @param layerID
	 *            layerID
	 * @return OptionFilter layerID에 해당하는
	 *         {@link com.git.gdsbuilder.type.validate.option.specific.OptionFilter}
	 * 
	 * @author DY.Oh
	 */
	public OptionFilter getLayerFilter(String layerID) {

		if (filter != null) {
			for (OptionFilter layerFilter : filter) {
				String code = layerFilter.getLayerID();
				if (layerID.equals(code)) {
					return layerFilter;
				}
			}
		}
		return null;
	}

	/**
	 * layerID에 해당하는
	 * {@link com.git.gdsbuilder.type.validate.option.specific.OptionFigure} 반환
	 * 
	 * @param layerID
	 *            layerID
	 * @return OptionFigure layerID에 해당하는
	 *         {@link com.git.gdsbuilder.type.validate.option.specific.OptionFigure}
	 * 
	 * @author DY.Oh
	 */
	public OptionFigure getLayerFigure(String layerID) {

		if (figure != null) {
			for (OptionFigure layerFigure : figure) {
				String code = layerFigure.getLayerID();
				if (layerID.equals(code)) {
					return layerFigure;
				}
			}
		}
		return null;
	}

	/**
	 * layerID에 해당하는
	 * {@link com.git.gdsbuilder.type.validate.option.specific.OptionTolerance}
	 * 반환
	 * 
	 * @param layerID
	 *            layerID
	 * @return OptionTolerance layerID에 해당하는
	 *         {@link com.git.gdsbuilder.type.validate.option.specific.OptionTolerance}
	 * 
	 * @author DY.Oh
	 */
	public OptionTolerance getLayerTolerance(String layerID) {

		if (tolerance != null) {
			for (OptionTolerance layerTolerance : tolerance) {
				String code = layerTolerance.getLayerID();
				if (code == null || layerID.equals(code)) {
					return layerTolerance;
				}
			}
		}
		return null;
	}

}
