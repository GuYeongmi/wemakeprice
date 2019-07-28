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
		
		//�������⸦ ������ Ŭ���̾�Ʈ ��ü ����
		HttpPost http = new HttpPost(url);
        HttpClient httpClient = HttpClientBuilder.create().build();

        //���� �� ���� �����͸� Response ��ü�� ����
        HttpResponse httpResponse = null;

        try {
        	httpResponse = httpClient.execute(http);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Response ���� ������ �� , DOM �����͸� ������ Entity�� ����
        HttpEntity entity = httpResponse.getEntity();

        //Charset�� �˾Ƴ��� ���� DOM�� ����Ʈ Ÿ���� ������ ��� Charset�� ������
        ContentType contentType= ContentType.getOrDefault(entity);
        Charset charset= contentType.getCharset();

        //DOM �����͸� �� �پ� �б����� Reader�� ����
        BufferedReader buffer = null;

        try {
        	buffer = new BufferedReader(new InputStreamReader(entity.getContent()));

        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();

        //DOM������ ������ ����

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
