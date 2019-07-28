package com.wemake.gu.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public interface ValidationService {

	HashMap<String, Object> checkValidation(HttpServletRequest request) throws Exception;
}
