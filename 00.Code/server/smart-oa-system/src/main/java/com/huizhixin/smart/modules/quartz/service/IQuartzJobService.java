package com.huizhixin.smart.modules.quartz.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.smart.modules.quartz.entity.QuartzJob;

/**
 * @Description: 定时任务在线管理
 * @Author:
 * @Date: 2019-04-28
 * @Version: V1.1
 */
public interface IQuartzJobService extends IService<QuartzJob> {

	List<QuartzJob> findByJobClassName(String jobClassName);

	boolean saveAndScheduleJob(QuartzJob quartzJob);

	boolean editAndScheduleJob(QuartzJob quartzJob) throws SchedulerException;

	boolean deleteAndStopJob(QuartzJob quartzJob);

	boolean resumeJob(QuartzJob quartzJob);
}
