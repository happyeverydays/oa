package com.huizhixin.integrated.mapper;

import java.util.List;

import com.huizhixin.integrated.entity.result.CompanyListBean;
import org.apache.ibatis.annotations.Param;
import com.huizhixin.integrated.entity.MonitorAlarm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 告警
 * @Author: smart-boot
 * @Date:   2020-01-06
 * @Version: V1.0
 */
public interface MonitorAlarmMapper extends BaseMapper<MonitorAlarm> {

    String getChildCompany(String companyid);

    List<CompanyListBean> getCompanyList(String companyId);
}
