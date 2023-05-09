package com.huizhixin.integrated.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.integrated.entity.AttendRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.integrated.entity.result.AttendStaticsBean;

import java.util.Map;

/**
 * @Description: 考勤记录
 * @Author: smart-boot
 * @Date:   2020-01-13
 * @Version: V1.0
 */
public interface IAttendRecordService extends IService<AttendRecord> {

    AttendRecord getDataByUserId(String userid);

    IPage<AttendStaticsBean> getAttendStatics(Page<AttendStaticsBean> page, Map<String,Object> attendRecord);
}
