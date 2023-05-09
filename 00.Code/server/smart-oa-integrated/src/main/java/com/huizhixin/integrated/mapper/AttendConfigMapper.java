package com.huizhixin.integrated.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.integrated.entity.AttendLateTimConf;
import com.huizhixin.integrated.entity.result.AttendConfigUserBen;
import org.apache.ibatis.annotations.Param;
import com.huizhixin.integrated.entity.AttendConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 打卡配置
 * @Author: smart-boot
 * @Date: 2020-01-13
 * @Version: V1.0
 */
public interface AttendConfigMapper extends BaseMapper<AttendConfig> {

    List<AttendConfigUserBen> getUserCardConfigList(@Param("page") Page<AttendConfigUserBen> page, @Param("attendConfig") AttendConfig attendConfig);

    Integer getUserCardConfigTotal( @Param("attendConfig") AttendConfig attendConfig);

    void editUserCardConfig(Map<String, String> map);

    List<AttendLateTimConf> getAttendConfList(String code);

    void updateAttendConf(AttendLateTimConf model);
}
