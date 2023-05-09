package com.huizhixin.integrated.service.impl;

import com.huizhixin.integrated.entity.MeasureHistory;
import com.huizhixin.integrated.entity.result.ElectricEveryDayCostBean;
import com.huizhixin.integrated.entity.result.ProcostElectricBean;
import com.huizhixin.integrated.mapper.MeasureHistoryMapper;
import com.huizhixin.integrated.service.IMeasureHistoryService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 测量历史
 * @Author: smart-boot
 * @Date: 2019-11-07
 * @Version: V1.0
 */
@Service
public class MeasureHistoryServiceImpl extends ServiceImpl<MeasureHistoryMapper, MeasureHistory> implements IMeasureHistoryService {

    @Override
    public List<ElectricEveryDayCostBean> getElectricEveryDayCostBeanList(Integer id) {
        Map<String, String> map = new HashMap<>();
        List<ElectricEveryDayCostBean> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dtstr = sdf.format(new Date());
        list.add(getElectricEveryDayCostBean(dtstr + " 00:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 02:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 04:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 06:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 08:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 10:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 12:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 14:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 16:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 18:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 20:00:00"));
        list.add(getElectricEveryDayCostBean(dtstr + " 22:00:00"));

        return list;
    }

    @Override
    public List<ProcostElectricBean> getcostelectrictodayrate(Integer id) {
        List<ProcostElectricBean> list = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dtstr = sdf.format(new Date());

        String todaycount = getcostelectrictodayrateBean(dtstr);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        dtstr = sdf.format(cal.getTime());
        String yesterdaycount = getcostelectrictodayrateBean(dtstr);

        list.add(new ProcostElectricBean("当日耗电", todaycount));
        list.add(new ProcostElectricBean("昨日耗电", yesterdaycount));
        Double huangbi = (new Double(todaycount)) / (new Double(yesterdaycount)) * 100 - 100;
        if (yesterdaycount.equals("0")) {
            huangbi = 0d;
        }
        list.add(new ProcostElectricBean("环比", String.format("%.2f", huangbi)));

        return list;
    }


    private String getcostelectrictodayrateBean(String dt) {
        Map<String, String> map = new HashMap<>();
        map.put("dt", dt);
        map.put("deviceno", "Energy-1");

        Double count = baseMapper.getcostelectrictodayTotal(map);
        map.put("deviceno", "Energy-4");

        count += baseMapper.getcostelectrictodayTotal(map);

        String result = String.format("%.2f", count);
        return result;
    }


    private ElectricEveryDayCostBean getElectricEveryDayCostBean(String dt) {
        ElectricEveryDayCostBean bean = new ElectricEveryDayCostBean();
        bean.setName(dt.substring(10, 16));

        Map<String, String> map = new HashMap<>();
        map.put("dt", dt);
        map.put("deviceno", "Energy-1");

        Double count = baseMapper.getElectricTotal(map);
        map.put("deviceno", "Energy-4");

        count += baseMapper.getElectricTotal(map);

        bean.setCount(String.format("%.2f", count));
        return bean;
    }
}
