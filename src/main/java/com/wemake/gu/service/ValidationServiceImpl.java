package com.wemake.gu.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.wemake.gu.util.WebValidationCheck;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Override
	public HashMap<String, Object> checkValidation(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", true);
		
		String url = request.getParameter("url");
		String htmlTag= request.getParameter("html");
		int unt = Integer.parseInt(request.getParameter("unt").toString());
		
		if(!WebValidationCheck.checkString(url)){
			resultMap.put("result", false);
			resultMap.put("check", "url");
			return resultMap;
		}
		
		if(!WebValidationCheck.checkString(htmlTag)){
			resultMap.put("result", false);
			resultMap.put("check", "htmlTag");
			return resultMap;
		}
		
		if(!WebValidationCheck.checkInt(unt)){
			resultMap.put("result", false);
			resultMap.put("check", "unt");
			return resultMap;
		}
		
		return resultMap;
	}
}
