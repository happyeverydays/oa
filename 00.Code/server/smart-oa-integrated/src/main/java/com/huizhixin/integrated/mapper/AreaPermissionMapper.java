package com.huizhixin.integrated.mapper;

import com.huizhixin.integrated.entity.AreaPermission;
import com.huizhixin.integrated.entity.result.AreaPermissonResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2019-09-11
 * @Version: V1.0
 */
public interface AreaPermissionMapper extends BaseMapper<AreaPermission> {

    AreaPermissonResult getArearPermission(String id);
}
