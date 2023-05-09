package com.huizhixin.integrated.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.integrated.entity.Bigaddress;
import com.huizhixin.integrated.entity.MonitorAlarm;
import com.huizhixin.integrated.entity.result.*;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.util.oConvertUtils;
import java.util.List;

public interface IBigScreenService extends IService<MyCompany> {

    List<MyCompanyThreeBean> getMyCompanyThreeList(String companyId);

    List<MyCompanyThreeBean> getMyCompanyAreaThree(String companyId);
}
