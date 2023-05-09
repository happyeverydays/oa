package com.huizhixin.smart.modules.demo.test.service;

import com.huizhixin.smart.common.system.base.service.JeecgService;
import com.huizhixin.smart.modules.demo.test.entity.JeecgDemo;

/**
 * @Description: jeecg 测试demo
 * @Author:
 * @Date:  2018-12-29
 * @Version: V1.0
 */
public interface IJeecgDemoService extends JeecgService<JeecgDemo> {
	
	public void testTran();
	
	public JeecgDemo getByIdCacheable(String id);
}
