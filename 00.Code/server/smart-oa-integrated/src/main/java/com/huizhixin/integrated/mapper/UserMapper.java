package com.huizhixin.integrated.mapper;

import com.huizhixin.integrated.entity.User;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 用户
 * @Author: jeecg-boot
 * @Date:   2019-08-24
 * @Version: V1.0
 */
public interface UserMapper extends BaseMapper<User> {

    User getUserByJobId(String accountName);
    
    User getUserByName(String name);
}
