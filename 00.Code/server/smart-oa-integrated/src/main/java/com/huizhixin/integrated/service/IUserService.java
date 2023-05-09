package com.huizhixin.integrated.service;

import com.huizhixin.integrated.entity.User;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 用户
 * @Author: jeecg-boot
 * @Date:   2019-08-24
 * @Version: V1.0
 */
public interface IUserService extends IService<User> {

    User getUserByJobId(String accountName);

    User getUserByName(String name);
}
