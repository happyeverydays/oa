package com.huizhixin.integrated.service.impl;

import com.huizhixin.integrated.entity.MeasurePoints;
import com.huizhixin.integrated.mapper.MeasurePointsMapper;
import com.huizhixin.integrated.service.IMeasurePointsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 测量点
 * @Author: smart-boot
 * @Date: 2019-11-07
 * @Version: V1.0
 */
@Service
public class MeasurePointsServiceImpl extends ServiceImpl<MeasurePointsMapper, MeasurePoints> implements IMeasurePointsService {

    @Override
    public MeasurePoints getDeviceno(String s) {

        return baseMapper.getDeviceno(s);
    }
}
