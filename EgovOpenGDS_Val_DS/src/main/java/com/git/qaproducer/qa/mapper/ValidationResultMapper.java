package com.git.qaproducer.qa.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;

import com.git.qaproducer.qa.domain.ValidationResult;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("validationResultMapper")
public interface ValidationResultMapper {

	int countValidationResultByUidx(int idx);

	JSONArray retrieveValidationResultByUidx(Map<String, Object> params);

	ValidationResult retrieveValidationResultByPidx(int idx);

	int deleteValidationResults(ArrayList<ValidationResult> vrList);

	int deleteValidationResult(ValidationResult result);
}
