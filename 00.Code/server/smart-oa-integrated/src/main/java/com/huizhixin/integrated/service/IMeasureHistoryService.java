package com.huizhixin.integrated.service;

import com.huizhixin.integrated.entity.MeasureHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.integrated.entity.result.ElectricEveryDayCostBean;
import com.huizhixin.integrated.entity.result.ProcostElectricBean;

import java.util.List;

/**
 * @Description: 测量历史
 * @Author: smart-boot
 * @Date:   2019-11-07
 * @Version: V1.0
 */
public interface IMeasureHistoryService extends IService<MeasureHistory> {

    List<ElectricEveryDayCostBean> getElectricEveryDayCostBeanList(Integer id);

    List<ProcostElectricBean> getcostelectrictodayrate(Integer id);
}
