package com.huizhixin.smart.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformHead;

public interface OnlCgformHeadMapper extends BaseMapper<OnlCgformHead> {
	void executeDDL(@Param("sqlStr") String var1);

	List<Map<String, Object>> queryList(@Param("sqlStr") String var1);

	List<String> queryOnlinetables();

	Map<String, Object> queryOneByTableNameAndId(@Param("tbname") String var1, @Param("dataId") String var2);

	void deleteOne(@Param("sqlStr") String var1);
}