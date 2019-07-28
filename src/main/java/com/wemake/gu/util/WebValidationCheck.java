package com.wemake.gu.util;

public class WebValidationCheck {
	//string value
	public static boolean checkString(String checkValue) {
		boolean returnValue = true;
		
		if(checkValue == null || checkValue.equals("") || checkValue.length() == 0) {
			returnValue = false;
		}
		
		return returnValue;
	}
	
	//int value
	public static boolean checkInt(int checkValue) {
		boolean returnValue = true;
		
		if(checkValue == 0) {
			returnValue = false;
		}
		
		return returnValue;
	}
}
