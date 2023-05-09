package com.huizhixin.smart.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huizhixin.smart.modules.system.entity.SysDepart;
import com.huizhixin.smart.modules.system.model.SysDepartTreeModel;
import com.huizhixin.smart.modules.system.model.TreeModel;

import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: Steve
 * @Since：   2019-01-22
 */
public interface SysDepartMapper extends BaseMapper<SysDepart> {
	
	/**
	 * 根据用户ID查询部门集合
	 */
	public List<SysDepart> queryUserDeparts(@Param("userId") String userId);

    Integer getIsAdmin(String id);
}
