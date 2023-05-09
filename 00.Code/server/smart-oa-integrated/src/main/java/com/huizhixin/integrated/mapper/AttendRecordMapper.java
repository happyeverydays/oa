package com.huizhixin.integrated.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.integrated.entity.result.AttendConfigUserBen;
import com.huizhixin.integrated.entity.result.AttendStaticsBean;
import org.apache.ibatis.annotations.Param;
import com.huizhixin.integrated.entity.AttendRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 考勤记录
 * @Author: smart-boot
 * @Date: 2020-01-13
 * @Version: V1.0
 */
public interface AttendRecordMapper extends BaseMapper<AttendRecord> {

    AttendRecord getDataByUserId(String userid);

    List<AttendStaticsBean> getAttendStaticsList(@Param("page") Page<AttendStaticsBean> page, @Param("attendRecord") Map<String,Object> attendRecord);

    Integer getAttendStaticsTotal(@Param("attendRecord") Map<String,Object> attendRecord);
}
