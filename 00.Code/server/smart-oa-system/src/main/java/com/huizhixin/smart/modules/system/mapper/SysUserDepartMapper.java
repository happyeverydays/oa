package com.huizhixin.smart.modules.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huizhixin.smart.modules.system.entity.SysUserDepart;

import io.lettuce.core.dynamic.annotation.Param;

public interface SysUserDepartMapper extends BaseMapper<SysUserDepart>{
	
	List<SysUserDepart> getUserDepartByUid(@Param("userId") String userId);
}
