package com.huizhixin.smart.modules.online.cgform.enhance.a;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import com.huizhixin.smart.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import com.huizhixin.smart.modules.online.config.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component("cgformEnhanceBeanDemo")
public class a implements CgformEnhanceJavaInter {
	public void execute(String tableName, JSONObject json) throws BusinessException {
		System.out.println("--------我是自定义java增强测试bean-----------");
		System.out.println("--------当前tableName=>" + tableName);
		System.out.println("--------当前JSON数据=>" + json.toJSONString());
	}

	public void execute(String tableName, Map<String, Object> map) throws BusinessException {
	}
}