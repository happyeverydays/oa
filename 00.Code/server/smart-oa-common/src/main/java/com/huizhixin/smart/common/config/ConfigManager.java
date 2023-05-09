/*
 */
package com.huizhixin.smart.common.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * 配置项管理类
 * 
 */
@Slf4j
@Service
public final class ConfigManager
{
    /**
     * 存储配置项的map对象，key为configName
     */
    private static Map<String, ConfigInfo> configMap = new HashMap<String, ConfigInfo>();
    
    /**
     * 配置项DAO类，必须通过注入DAO才能获得数据库的连接 这里也不能用接口来定义
     */
    @Autowired
    private ConfigInfoService configInfoService;
    
    /**
     * 通过Spring来调用该方法
     * 
     * @throws Exception 初始化失败要中断服务启动
     * 
     * @see [类、类#方法、类#成员]
     */
    @PostConstruct
    public void init()
        throws Exception
    {
        try
        {
            // 这里可以获取DAO了，调用真正初始化方法
            // 这里的实现有点特殊，为了方便其它模块获取配置项，其它模块不能参考这里实现
            ConfigManager.initConfig(configInfoService);
        }
        catch (Exception ex)
        {
            log.error(ex.toString());
            //throw ex;
        }
    }
    
    /**
     * 初始化方法，查询数据库表将配置项存到configMap中
     * 
     * @param configDao DAO类，通过该类可以获得数据库连接
     * @throws Exception 异常
     * @see [类、类#方法、类#成员]
     */
    public static void initConfig(final ConfigInfoService configInfoService)
        throws Exception
    {
        
        List<ConfigInfo> configs = configInfoService.list();
        if (configs != null && !configs.isEmpty())
        {
            for (ConfigInfo config : configs)
            {
                configMap.put(config.getConfigName(), config);
            }
        }
    }
    
    /**
     * 根据配置项名称获取配置项值
     * 
     * @param configName 配置项名称
     * @return configValue 配置项值
     */
    public static String getConfigValue(String configName)
    {
        // 该方法里不能打印日志，否则出现循环依赖
        if (configMap != null)
        {
            ConfigInfo config = configMap.get(configName);
            if (config != null)
            {
                String val = config.getConfigValue();
                if (null != val)
                {
                    val = val.trim();
                }
                return val;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
}
