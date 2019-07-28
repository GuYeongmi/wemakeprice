package com.wemake.gu.util;

import java.util.HashMap;

public class Common {
	//remove the element
	public static String[] removeElement(String[] arr, int index) { 
        
		if (arr == null || index >= arr.length) { 
            return arr; 
        } 
  
        String[] anotherArray = new String[arr.length - 1]; 
  
        System.arraycopy(arr, 0, anotherArray, 0, index); 
        System.arraycopy(arr, index + 1,anotherArray, index, arr.length - index - 1); 
  
        return anotherArray; 
	}
	
	//common string extrac
	public static HashMap<String, Object> stringExtrac(int num, String[] arr1, String[] arr2) {
		
		HashMap<String, Object> resultMap = new HashMap<>();
		String resultString = "";
		
		for(int i=0; i<num; i++) {
			if(i%2 == 0){
				if(arr1.length > 0) {
					resultString += arr1[0];
					arr1 = removeElement(arr1, 0);
				} else {
					resultString += arr2[0].toString();
					arr2 = removeElement(arr2, 0);
				}
			} else {
				if(arr2.length > 0) {
					resultString += arr2[0].toString();
					arr2 = removeElement(arr2, 0);
				} else {
					resultString += arr1[0];
					arr1 = removeElement(arr1, 0);
				}
			}
		}
		
		resultMap.put("resultString", resultString);
		resultMap.put("remArr1", arr1);
		resultMap.put("remArr2", arr2);
		return resultMap;
	}
	
	//regular expression
	public static String regValueAll(String orgData, String reg, String changeValue) {
		
		String retunValue = orgData.replaceAll(reg, changeValue);
				
		return retunValue;
	}
	
}
