package com.huizhixin.smart.modules.online.cgform.service;

import java.util.List;
import java.util.Map;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;

public interface IOnlCgformSqlService {
	void saveBatchOnlineTable(String var1, List<OnlCgformField> var2, List<Map<String, Object>> var3);
}