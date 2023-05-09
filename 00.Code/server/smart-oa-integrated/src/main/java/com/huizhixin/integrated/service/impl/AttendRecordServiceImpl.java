package com.huizhixin.integrated.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.integrated.entity.AttendRecord;
import com.huizhixin.integrated.entity.result.AttendConfigUserBen;
import com.huizhixin.integrated.entity.result.AttendStaticsBean;
import com.huizhixin.integrated.mapper.AttendRecordMapper;
import com.huizhixin.integrated.service.IAttendRecordService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 考勤记录
 * @Author: smart-boot
 * @Date: 2020-01-13
 * @Version: V1.0
 */
@Service
public class AttendRecordServiceImpl extends ServiceImpl<AttendRecordMapper, AttendRecord> implements IAttendRecordService {

    @Override
    public AttendRecord getDataByUserId(String userid) {
        return baseMapper.getDataByUserId(userid);
    }

    @Override
    public IPage<AttendStaticsBean> getAttendStatics(Page<AttendStaticsBean> page, Map<String,Object> attendRecord) {
        IPage<AttendStaticsBean> p = new Page<>();
        List<AttendStaticsBean> list = baseMapper.getAttendStaticsList(page, attendRecord);
        Integer total = baseMapper.getAttendStaticsTotal(attendRecord);
        p.setRecords(list);
        p.setTotal(total);

        return p;
    }
}
