package com.wemake.gu.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import com.wemake.gu.util.Common;

@Service
public class WeMakePriceServiceInterviewImpl implements WeMakePriceInterviewService{
	
	@Override
	public HashMap<String, Object> getDomData(String url, String htmlTag, int unt) throws Exception {
		
		//가져오기를 실행할 클라이언트 객체 생성
		HttpPost http = new HttpPost(url);
        HttpClient httpClient = HttpClientBuilder.create().build();

        //실행 및 실행 데이터를 Response 객체에 담음
        HttpResponse httpResponse = null;

        try {
        	httpResponse = httpClient.execute(http);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Response 받은 데이터 중 , DOM 데이터를 가져와 Entity에 담음
        HttpEntity entity = httpResponse.getEntity();

        //Charset을 알아내기 위해 DOM의 컨텐트 타입을 가져와 담고 Charset을 가져옴
        ContentType contentType= ContentType.getOrDefault(entity);
        Charset charset= contentType.getCharset();

        //DOM 데이터를 한 줄씩 읽기위해 Reader에 담음
        BufferedReader buffer = null;

        try {
        	buffer = new BufferedReader(new InputStreamReader(entity.getContent()));

        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();

        //DOM데이터 가지고 오기

        String line="";
        String alpData = "";
        String intData = "";

        try {
            while((line=buffer.readLine()) != null) {
            	if(htmlTag.equals("non")) {
            		line = Common.regValueAll(line, "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
            	}
                
            	alpData += Common.regValueAll(line, "[^a-zA-Z]", "");
            	intData += Common.regValueAll(line, "[^0-9]", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String alpArray[] = alpData.split("");
        String intArray[] = intData.split("");
        
        Arrays.sort(alpArray);
        Arrays.sort(alpArray, String.CASE_INSENSITIVE_ORDER);
        Arrays.sort(intArray);
        
        int dataLen = alpArray.length + intArray.length;
        int quot = dataLen / unt;
        int rem = dataLen % unt;
        
        String quotString = "";
        String remString = "";
        
        if(dataLen >= unt) {
        	HashMap<String, Object> quotMap;        	

        	for(int i=0; i<quot; i++) {
        		quotMap = Common.stringExtrac(unt, alpArray, intArray);
        		quotString += quotMap.get("resultString");
        		alpArray = (String[]) quotMap.get("remArr1");
        		intArray = (String[]) quotMap.get("remArr2");
        	}
        }
        
        HashMap<String, Object> remMap = Common.stringExtrac(rem, alpArray, intArray);
        remString = (String) remMap.get("resultString");
        
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("quot", quotString);
        resultMap.put("rem", remString);
		
		return resultMap;
	}

}
