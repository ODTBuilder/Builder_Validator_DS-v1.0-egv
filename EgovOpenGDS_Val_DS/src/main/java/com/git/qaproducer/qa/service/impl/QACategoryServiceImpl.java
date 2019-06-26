/**
 * 
 */
package com.git.qaproducer.qa.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.git.qaproducer.qa.domain.QACategory;
import com.git.qaproducer.qa.repository.QACategoryRepository;
import com.git.qaproducer.qa.service.QACategoryService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("qaCategoryService")
@Transactional
public class QACategoryServiceImpl extends EgovAbstractServiceImpl implements QACategoryService {

	@Resource(name = "qaCategoryRepository")
	private QACategoryRepository qaCategoryRepository;

	@Transactional(readOnly = true)
	public QACategory retrieveQACategoryByIdx(int idx) {
		return qaCategoryRepository.retrieveQACategoryByIdx(idx);
	}

}
