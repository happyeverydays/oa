package com.huizhixin.integrated.service;

import com.huizhixin.integrated.entity.MonitorAlarm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.integrated.entity.result.CompanyListBean;

import java.util.List;

/**
 * @Description: 告警
 * @Author: smart-boot
 * @Date:   2020-01-06
 * @Version: V1.0
 */
public interface IMonitorAlarmService extends IService<MonitorAlarm> {
    String getChildCompany(String companyid);

    List<CompanyListBean> getCompanyList(String companyId);
}
