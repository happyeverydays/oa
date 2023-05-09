package com.huizhixin.smart.modules.online.cgform.enhance.a;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import com.huizhixin.smart.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import com.huizhixin.smart.modules.online.config.exception.BusinessException;

public class b implements CgformEnhanceJavaInter {
	public void execute(String tableName, Map<String, Object> map) throws BusinessException {
	}

	public void execute(String tableName, JSONObject json) throws BusinessException {
		System.out.println("----------我是自定义java增强测试demo类-----------");
		System.out.println("--------当前tableName=>" + tableName);
		System.out.println("--------当前JSON数据=>" + json.toJSONString());
	}
}