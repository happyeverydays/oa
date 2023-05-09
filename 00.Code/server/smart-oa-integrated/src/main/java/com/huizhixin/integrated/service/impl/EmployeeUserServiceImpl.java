package com.huizhixin.integrated.service.impl;

import com.huizhixin.integrated.entity.User;
import com.huizhixin.integrated.entity.result.DictItemBean;
import com.huizhixin.integrated.mapper.EmployeeUserMapper;
import com.huizhixin.integrated.service.IEmployeeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 用户
 * @Author: jeecg-boot
 * @Date:   2019-08-24
 * @Version: V1.0
 */
@Service
public class EmployeeUserServiceImpl extends ServiceImpl<EmployeeUserMapper, User> implements IEmployeeUserService {

    @Autowired
    private EmployeeUserMapper userMapper;
    @Override
    public User getUserByJobId(String accountName) {

        return userMapper.getUserByJobId(accountName);
    }

    @Override
    public List<DictItemBean> geyDictgroupList(String dictname) {
        return userMapper.geyDictgroupList(dictname);
    }

    @Override
    public void saveDictgroupList(List<DictItemBean> list) {
        for(DictItemBean bean :list) {
            userMapper.saveDictgroupList(bean);
        }
    }


}
