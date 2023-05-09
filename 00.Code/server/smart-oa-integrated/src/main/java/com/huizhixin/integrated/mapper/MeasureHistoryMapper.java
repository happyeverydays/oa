package com.huizhixin.integrated.mapper;

import java.util.List;
import java.util.Map;

import com.huizhixin.integrated.entity.MeasureHistory;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 测量历史
 * @Author: smart-boot
 * @Date: 2019-11-07
 * @Version: V1.0
 */
public interface MeasureHistoryMapper extends BaseMapper<MeasureHistory> {

    Double getElectricTotal(Map<String, String> map);

    Double getcostelectrictodayTotal(Map<String, String> map);
}
