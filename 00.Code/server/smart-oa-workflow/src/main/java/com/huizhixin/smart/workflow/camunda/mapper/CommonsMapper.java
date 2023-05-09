package com.huizhixin.smart.workflow.camunda.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * @author wushuo <2807972772@qq.com>
 *
 */
public interface CommonsMapper {
	@Select(" select * from ${tableName} where id = #{businessKey , jdbcType=VARCHAR}")
	public Map<String, Object> getFormData(String tableName, String businessKey);

	@Select("${selectSql}")
	public List<Map<String, Object>> getVarData(@Param("selectSql")String selectSql);
	
	@Select("${selectSql}")
	public List<String> getOneFieldVarData(@Param("selectSql")String selectSql);

}
