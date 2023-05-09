package com.huizhixin.smart.modules.online.cgform.service.a;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformButton;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformButtonMapper;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformButtonService;
import org.springframework.stereotype.Service;

@Service("onlCgformButtonServiceImpl")
public class a extends ServiceImpl<OnlCgformButtonMapper, OnlCgformButton> implements IOnlCgformButtonService {
}