package com.huizhixin.smart.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;

public interface OnlCgformFieldMapper extends BaseMapper<OnlCgformField> {
	List<Map<String, Object>> queryListBySql(@Param("sqlStr") String var1);

	Integer queryCountBySql(@Param("sqlStr") String var1);

	void deleteAutoList(@Param("sqlStr") String var1);

	void saveFormData(@Param("sqlStr") String var1);

	void editFormData(@Param("sqlStr") String var1);

	Map<String, Object> queryFormData(@Param("sqlStr") String var1);

	List<Map<String, Object>> queryListData(@Param("sqlStr") String var1);

	IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> var1, @Param("sqlStr") String var2);

	void executeInsertSQL(Map<String, Object> var1);

	void executeUpdatetSQL(Map<String, Object> var1);

	List<String> selectOnlineHideColumns(@Param("user_id") String var1, @Param("online_tbname") String var2);

	List<String> selectOnlineDisabledColumns(@Param("user_id") String var1, @Param("online_tbname") String var2);
}