package com.huizhixin.integrated.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huizhixin.integrated.entity.AreaPermission;
import com.huizhixin.integrated.entity.result.AreaPermissonResult;
import com.huizhixin.integrated.mapper.AreaPermissionMapper;
import com.huizhixin.integrated.service.IAreaPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date: 2019-09-11
 * @Version: V1.0
 */
@Service
public class AreaPermissionServiceImpl extends ServiceImpl<AreaPermissionMapper, AreaPermission> implements IAreaPermissionService {

    @Autowired
    AreaPermissionMapper areaPermissionMapper;

    @Override
    public AreaPermissonResult getArearPermission(String id) {
        return areaPermissionMapper.getArearPermission(id);
    }

    @Override
    public AreaPermissonResult saveAreaPermission(String id, String areaperssion) {

        Map<String, Object> map = new HashMap<>();
        map.put("userid", id);
        areaPermissionMapper.deleteByMap(map);
        String[] permissionList = areaperssion.split(",");
        for (String peremissionid : permissionList) {
            AreaPermission area=new AreaPermission();
            area.setUserid(id);
            area.setArearid(Integer.parseInt(peremissionid) );
            area.setType(2);
            areaPermissionMapper.insert(area);
        }
        return null;
    }
}
