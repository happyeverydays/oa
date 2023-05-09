package com.huizhixin.integrated.service;

import com.huizhixin.integrated.entity.AreaPermission;
import com.huizhixin.integrated.entity.result.AreaPermissonResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2019-09-11
 * @Version: V1.0
 */
public interface IAreaPermissionService extends IService<AreaPermission> {

    AreaPermissonResult getArearPermission(String id);

    AreaPermissonResult saveAreaPermission(String id, String areaperssion);
}
