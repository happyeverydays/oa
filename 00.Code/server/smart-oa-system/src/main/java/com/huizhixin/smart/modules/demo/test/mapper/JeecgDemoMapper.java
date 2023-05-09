package com.huizhixin.smart.modules.demo.test.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huizhixin.smart.modules.demo.test.entity.JeecgDemo;

/**
 * @Description: jeecg 测试demo
 * @Author:
 * @Date:  2018-12-29
 * @Version: V1.0
 */
public interface JeecgDemoMapper extends BaseMapper<JeecgDemo> {

	public List<JeecgDemo> getDemoByName(@Param("name") String name);

}
