package com.huizhixin.smart.workflow.camunda.util;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;



/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public class RequestParamUtil {
	
	public static MultivaluedMap<String,String> convertMultivaluedMap(Map<String, String[]> paramMap) {
		MultivaluedMap<String,String> multiMap = new MultivaluedHashMap<String,String>();
		Set<String> keys = paramMap.keySet();
		for (String key : keys) {
			String[] value = paramMap.get(key);
			
			multiMap.add(key, value[0]);
		}
		return multiMap;
	}

}
