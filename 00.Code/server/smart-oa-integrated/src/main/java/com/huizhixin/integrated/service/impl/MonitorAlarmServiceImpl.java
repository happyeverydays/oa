package com.huizhixin.integrated.service.impl;

import com.huizhixin.integrated.entity.MonitorAlarm;
import com.huizhixin.integrated.entity.result.CompanyListBean;
import com.huizhixin.integrated.mapper.MonitorAlarmMapper;
import com.huizhixin.integrated.service.IMonitorAlarmService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 告警
 * @Author: smart-boot
 * @Date: 2020-01-06
 * @Version: V1.0
 */
@Service
public class MonitorAlarmServiceImpl extends ServiceImpl<MonitorAlarmMapper, MonitorAlarm> implements IMonitorAlarmService {

    @Override
    public String getChildCompany(String companyid) {
        return baseMapper.getChildCompany(companyid);
    }

    @Override
    public List<CompanyListBean> getCompanyList(String companyId) {
        return baseMapper.getCompanyList(companyId);
    }
}
