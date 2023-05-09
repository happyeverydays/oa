package com.huizhixin.smart.modules.quartz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huizhixin.smart.modules.quartz.entity.QuartzJob;

/**
 * @Description: 定时任务在线管理
 * @Author:
 * @Date:  2019-01-02
 * @Version: V1.0
 */
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

	public List<QuartzJob> findByJobClassName(@Param("jobClassName") String jobClassName);

}
