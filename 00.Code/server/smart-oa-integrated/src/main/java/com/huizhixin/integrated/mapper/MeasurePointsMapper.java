package com.huizhixin.integrated.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.huizhixin.integrated.entity.MeasurePoints;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 测量点
 * @Author: smart-boot
 * @Date:   2019-11-07
 * @Version: V1.0
 */
public interface MeasurePointsMapper extends BaseMapper<MeasurePoints> {


    MeasurePoints getDeviceno(String s);
}
