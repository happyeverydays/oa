package com.huizhixin.smart.modules.online.cgform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformIndex;

public interface IOnlCgformIndexService extends IService<OnlCgformIndex> {
	void createIndex(String var1, String var2, String var3);
}