/*
 * 文 件 名:  ConfigInfo.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  y00109551
 * 修改时间:  2009-8-12
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.huizhixin.smart.common.config;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 配置项module类
 * 
 * @author y00109551
 * @version [版本号, 2009-8-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@TableName("base_config")
public class ConfigInfo
{
    private String id;
    
    /**
     * 配置项名称
     */
    private String configName;
    
    /**
     * 配置项值
     */
    private String configValue;
    
    /**
     * 配置项描述
     */
    private String configDesc;
}
