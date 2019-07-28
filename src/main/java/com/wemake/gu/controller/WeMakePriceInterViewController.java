package com.wemake.gu.controller;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wemake.gu.service.ValidationService;
import com.wemake.gu.service.WeMakePriceInterviewService;
import com.wemake.gu.util.WebValidationCheck;

/**
 * Handles requests for the application home page.
 */
@Controller
public class WeMakePriceInterViewController {
	
	private static final Logger logger = LoggerFactory.getLogger(WeMakePriceInterViewController.class);
	
	MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
	
	@Autowired
	ValidationService validationService;
	@Autowired
	WeMakePriceInterviewService wemakeService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "wemakeprice";
	}
	
	@RequestMapping(value="/getUrlText.do", method=RequestMethod.GET)
	public void urlData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> validationMap = validationService.checkValidation(request);
		
		if(!(boolean) validationMap.get("result")) {
			
			jsonView.render(validationMap, request, response);
		}
			
		String url = request.getParameter("url");
		String htmlTag= request.getParameter("html");
		int unt = Integer.parseInt(request.getParameter("unt").toString());
		
		HashMap<String, Object> dataMap = wemakeService.getDomData(url, htmlTag, unt);
		
		HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", true);
		resultMap.put("quot", dataMap.get("quot"));
		resultMap.put("rem", dataMap.get("rem"));
		
		jsonView.render(resultMap, request, response);			
		
	}
	
}
