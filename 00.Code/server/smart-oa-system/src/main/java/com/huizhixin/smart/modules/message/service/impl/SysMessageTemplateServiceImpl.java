package com.huizhixin.smart.modules.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huizhixin.smart.common.system.base.service.impl.JeecgServiceImpl;
import com.huizhixin.smart.modules.message.entity.SysMessageTemplate;
import com.huizhixin.smart.modules.message.mapper.SysMessageTemplateMapper;
import com.huizhixin.smart.modules.message.service.ISysMessageTemplateService;

import java.util.List;

/**
 * @Description: 消息模板
 * @Author:
 * @Date:  2019-04-09
 * @Version: V1.0
 */
@Service
public class SysMessageTemplateServiceImpl extends JeecgServiceImpl<SysMessageTemplateMapper, SysMessageTemplate> implements ISysMessageTemplateService {

    @Autowired
    private SysMessageTemplateMapper sysMessageTemplateMapper;


    @Override
    public List<SysMessageTemplate> selectByCode(String code) {
        return sysMessageTemplateMapper.selectByCode(code);
    }
}
