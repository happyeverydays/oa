package com.huizhixin.integrated.mapper;

import com.huizhixin.integrated.entity.User;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huizhixin.integrated.entity.result.DictItemBean;

import java.util.List;

/**
 * @Description: 用户
 * @Author: jeecg-boot
 * @Date:   2019-08-24
 * @Version: V1.0
 */
public interface EmployeeUserMapper extends BaseMapper<User> {

    User getUserByJobId(String accountName);

    List<DictItemBean> geyDictgroupList(String dictname);

    void saveDictgroupList(DictItemBean bean);
}
