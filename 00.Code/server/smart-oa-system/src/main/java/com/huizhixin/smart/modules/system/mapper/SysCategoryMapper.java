package com.huizhixin.smart.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huizhixin.smart.modules.system.entity.SysCategory;
import com.huizhixin.smart.modules.system.model.TreeSelectModel;

/**
 * @Description: 分类字典
 * @Author:
 * @Date:   2019-05-29
 * @Version: V1.0
 */
public interface SysCategoryMapper extends BaseMapper<SysCategory> {
	
	/**
	  *  根据父级ID查询树节点数据
	 * @param pid
	 * @return
	 */
	public List<TreeSelectModel> queryListByPid(@Param("pid")  String pid);
	

}
