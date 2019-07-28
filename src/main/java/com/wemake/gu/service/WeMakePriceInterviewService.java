package com.wemake.gu.service;

import java.util.HashMap;

public interface WeMakePriceInterviewService {

	HashMap<String, Object> getDomData(String url, String htmlTag, int unt) throws Exception;
}
