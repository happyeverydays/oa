package com.huizhixin.smart.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformButton;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceJava;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceJs;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceSql;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformHead;
import com.huizhixin.smart.modules.online.cgform.model.OnlGenerateModel;
import com.huizhixin.smart.modules.online.cgform.model.a;
import com.huizhixin.smart.modules.online.config.exception.BusinessException;
import com.huizhixin.smart.modules.online.config.exception.DBException;

public interface IOnlCgformHeadService extends IService<OnlCgformHead> {
	Result<?> addAll(a var1);

	Result<?> editAll(a var1);

	void doDbSynch(String var1, String var2)
			throws HibernateException, IOException, TemplateException, SQLException, DBException;

	void deleteRecordAndTable(String var1) throws DBException, SQLException;

	List<Map<String, Object>> queryListData(String var1);

	OnlCgformEnhanceJs queryEnhance(String var1, String var2);

	void saveEnhance(OnlCgformEnhanceJs var1);

	void editEnhance(OnlCgformEnhanceJs var1);

	OnlCgformEnhanceSql queryEnhanceSql(String var1, String var2);

	void saveEnhance(OnlCgformEnhanceSql var1);

	void editEnhance(OnlCgformEnhanceSql var1);

	OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava var1);

	boolean checkOnlyEnhance(OnlCgformEnhanceJava var1);

	void saveEnhance(OnlCgformEnhanceJava var1);

	void editEnhance(OnlCgformEnhanceJava var1);

	List<OnlCgformButton> queryButtonList(String var1, boolean var2);

	List<OnlCgformButton> queryButtonList(String var1);

	List<String> queryOnlinetables();

	void saveDbTable2Online(String var1);

	JSONObject queryFormItem(OnlCgformHead var1);

	void saveManyFormData(String var1, JSONObject var2) throws DBException, BusinessException;

	Map<String, Object> queryManyFormData(String var1, String var2) throws DBException;

	List<Map<String, Object>> queryManySubFormData(String var1, String var2) throws DBException;

	Map<String, Object> querySubFormData(String var1, String var2) throws DBException;

	void editManyFormData(String var1, JSONObject var2) throws DBException, BusinessException;

	void executeEnhanceJava(String var1, String var2, OnlCgformHead var3, JSONObject var4) throws BusinessException;

	void executeEnhanceSql(String var1, String var2, JSONObject var3);

	void executeCustomerButton(String var1, String var2, String var3) throws BusinessException;

	List<OnlCgformButton> queryValidButtonList(String var1);

	OnlCgformEnhanceJs queryEnhanceJs(String var1, String var2);

	void deleteOneTableInfo(String var1, String var2) throws BusinessException;

	void generateCode(OnlGenerateModel var1) throws Exception;

	void generateOneToMany(OnlGenerateModel var1) throws Exception;

	void addCrazyFormData(String var1, JSONObject var2) throws DBException, UnsupportedEncodingException;

	void editCrazyFormData(String var1, JSONObject var2) throws DBException, UnsupportedEncodingException;
}