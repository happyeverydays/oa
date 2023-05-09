package com.huizhixin.integrated.service.impl;

import com.huizhixin.integrated.entity.User;
import com.huizhixin.integrated.mapper.UserMapper;
import com.huizhixin.integrated.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户
 * @Author: jeecg-boot
 * @Date:   2019-08-24
 * @Version: V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByJobId(String accountName) {

        return userMapper.getUserByJobId(accountName);
    }
    @Override
    public User getUserByName(String name)
    {
        return userMapper.getUserByName(name);
    }



}
