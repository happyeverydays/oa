package com.huizhixin.smart.modules.online.cgform.enhance;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import com.huizhixin.smart.modules.online.config.exception.BusinessException;

public interface CgformEnhanceJavaInter {
	void execute(String var1, Map<String, Object> var2) throws BusinessException;

	void execute(String var1, JSONObject var2) throws BusinessException;
}