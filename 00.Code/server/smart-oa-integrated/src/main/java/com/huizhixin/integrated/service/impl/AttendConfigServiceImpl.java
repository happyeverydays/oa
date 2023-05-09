package com.huizhixin.integrated.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.integrated.entity.AttendConfig;
import com.huizhixin.integrated.entity.AttendLateTimConf;
import com.huizhixin.integrated.entity.result.AttendConfigUserBen;
import com.huizhixin.integrated.mapper.AttendConfigMapper;
import com.huizhixin.integrated.service.IAttendConfigService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 打卡配置
 * @Author: smart-boot
 * @Date: 2020-01-13
 * @Version: V1.0
 */
@Service
public class AttendConfigServiceImpl extends ServiceImpl<AttendConfigMapper, AttendConfig> implements IAttendConfigService {

    @Override
    public IPage<AttendConfigUserBen> getUserCardConfigList(Page<AttendConfigUserBen> page, AttendConfig attendConfig) {
        IPage<AttendConfigUserBen> p = new Page<>();
        List<AttendConfigUserBen> list = baseMapper.getUserCardConfigList(page, attendConfig);
        Integer total = baseMapper.getUserCardConfigTotal(attendConfig);
        p.setRecords(list);
        p.setTotal(total);

        return p;
    }

    @Override
    public void editUserCardConfig(String id, String attendType) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("attendType", attendType);
        baseMapper.editUserCardConfig(map);
    }

    @Override
    public List<AttendLateTimConf> getAttendConfList(String code) {
        return baseMapper.getAttendConfList(code);
    }

    @Override
    public void updateAttendConf(AttendLateTimConf model) {
        baseMapper.updateAttendConf(model);
    }
}
