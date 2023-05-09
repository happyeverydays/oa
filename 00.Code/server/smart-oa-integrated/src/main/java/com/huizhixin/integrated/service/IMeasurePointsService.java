package com.huizhixin.integrated.service;

import com.huizhixin.integrated.entity.MeasurePoints;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 测量点
 * @Author: smart-boot
 * @Date:   2019-11-07
 * @Version: V1.0
 */
public interface IMeasurePointsService extends IService<MeasurePoints> {

    MeasurePoints getDeviceno(String s);
}
