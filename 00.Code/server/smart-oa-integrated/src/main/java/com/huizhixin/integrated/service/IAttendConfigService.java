package com.huizhixin.integrated.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.integrated.entity.AttendConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.integrated.entity.AttendLateTimConf;
import com.huizhixin.integrated.entity.result.AttendConfigUserBen;

import java.util.List;

/**
 * @Description: 打卡配置
 * @Author: smart-boot
 * @Date:   2020-01-13
 * @Version: V1.0
 */
public interface IAttendConfigService extends IService<AttendConfig> {

    IPage<AttendConfigUserBen> getUserCardConfigList(Page<AttendConfigUserBen> page, AttendConfig attendConfig);

    void editUserCardConfig(String id, String attendType);

    List<AttendLateTimConf> getAttendConfList(String code);

    void updateAttendConf(AttendLateTimConf model);
}
