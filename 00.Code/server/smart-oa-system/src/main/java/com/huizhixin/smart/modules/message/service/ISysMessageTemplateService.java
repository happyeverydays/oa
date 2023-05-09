package com.huizhixin.smart.modules.message.service;

import java.util.List;

import com.huizhixin.smart.common.system.base.service.JeecgService;
import com.huizhixin.smart.modules.message.entity.SysMessageTemplate;

/**
 * @Description: 消息模板
 * @Author:
 * @Date:  2019-04-09
 * @Version: V1.0
 */
public interface ISysMessageTemplateService extends JeecgService<SysMessageTemplate> {
    List<SysMessageTemplate> selectByCode(String code);
}
