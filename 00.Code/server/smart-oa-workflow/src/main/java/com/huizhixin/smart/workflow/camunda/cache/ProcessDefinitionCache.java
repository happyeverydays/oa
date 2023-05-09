package com.huizhixin.smart.workflow.camunda.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.huizhixin.smart.workflow.camunda.dto.ProcessDefinitionDto;

public class ProcessDefinitionCache {
	private static Map<String, List<ProcessDefinitionDto>> cache = new ConcurrentHashMap<String, List<ProcessDefinitionDto>>();
	private static List<ProcessDefinitionDto> processDefinitionDtoList = null;

	public static void setCache(String key, List<ProcessDefinitionDto> obj, long seconds) {
		cache.put(key, obj);
	}

	public static List<ProcessDefinitionDto> getCache(String key) {
		if (cache.get(key) == null) {
			
			processDefinitionDtoList = new ArrayList<ProcessDefinitionDto>();
			setCache(key, processDefinitionDtoList, 0);
			return processDefinitionDtoList;
		} else {
			return cache.get(key);
		}
	}

	public static ProcessDefinitionDto getLastVersionCache(String key) {
		ProcessDefinitionDto processDefinitionDto = null;
		if (cache.get(key) == null) {
			return null;
		} else {
			processDefinitionDto = getCache(key).get(getCache(key).size() - 1);
			return processDefinitionDto;
		}
	}

	public static void removeCache(String key) {
		cache.remove(key);

	}

}
