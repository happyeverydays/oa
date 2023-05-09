package com.huizhixin.smart.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;

public interface IOnlCgformFieldService extends IService<OnlCgformField> {
	Map<String, Object> queryAutolistPage(String var1, String var2, Map<String, Object> var3, List<String> var4);

	void deleteAutoList(String var1, String var2);

	void deleteAutoList(String var1, String var2, String var3);

	void saveFormData(String var1, String var2, JSONObject var3, boolean var4);

	void saveTreeFormData(String var1, String var2, JSONObject var3, String var4, String var5);

	void saveFormData(List<OnlCgformField> var1, String var2, JSONObject var3);

	List<OnlCgformField> queryFormFields(String var1, boolean var2);

	void editTreeFormData(String var1, String var2, JSONObject var3, String var4, String var5);

	void editFormData(String var1, String var2, JSONObject var3, boolean var4);

	Map<String, Object> queryFormData(String var1, String var2, String var3);

	Map<String, Object> queryFormData(List<OnlCgformField> var1, String var2, String var3);

	List<Map<String, Object>> querySubFormData(List<OnlCgformField> var1, String var2, String var3, String var4);

	List<Map<String, String>> getAutoListQueryInfo(String var1);

	IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> var1, @Param("sqlStr") String var2);

	List<String> selectOnlineHideColumns(String var1);

	List<OnlCgformField> queryAvailableFields(String var1, String var2, boolean var3);

	List<String> queryDisabledFields(String var1);

	List<OnlCgformField> queryAvailableFields(String var1, boolean var2, List<OnlCgformField> var3, List<String> var4);

	void executeInsertSQL(Map<String, Object> var1);

	void executeUpdatetSQL(Map<String, Object> var1);
}