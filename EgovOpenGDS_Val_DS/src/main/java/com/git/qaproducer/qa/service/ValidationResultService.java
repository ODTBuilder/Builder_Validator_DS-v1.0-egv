package com.git.qaproducer.qa.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import com.git.qaproducer.qa.domain.ServerSideVO;
import com.git.qaproducer.qa.domain.ValidationResult;

public interface ValidationResultService {
	public JSONObject retrieveValidationResultByUidx(HashMap<String, Object> input, ServerSideVO serverSideVO,
			int idx);
	
	public ValidationResult retrieveValidationResultByPidx(int idx);
	
	public boolean deleteValidationResult(ArrayList<ValidationResult> vrList);
}
