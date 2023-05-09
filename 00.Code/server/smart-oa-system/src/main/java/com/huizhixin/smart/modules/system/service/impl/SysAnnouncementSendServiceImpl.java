package com.huizhixin.smart.modules.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huizhixin.smart.modules.system.entity.SysAnnouncementSend;
import com.huizhixin.smart.modules.system.mapper.SysAnnouncementSendMapper;
import com.huizhixin.smart.modules.system.model.AnnouncementSendModel;
import com.huizhixin.smart.modules.system.service.ISysAnnouncementSendService;

/**
 * @Description: 用户通告阅读标记表
 * @Author:
 * @Date:  2019-02-21
 * @Version: V1.0
 */
@Service
public class SysAnnouncementSendServiceImpl extends ServiceImpl<SysAnnouncementSendMapper, SysAnnouncementSend> implements ISysAnnouncementSendService {

	@Resource
	private SysAnnouncementSendMapper sysAnnouncementSendMapper;
	
	@Override
	public List<String> queryByUserId(String userId) {
		return sysAnnouncementSendMapper.queryByUserId(userId);
	}

	@Override
	public Page<AnnouncementSendModel> getMyAnnouncementSendPage(Page<AnnouncementSendModel> page,
			AnnouncementSendModel announcementSendModel) {
		 return page.setRecords(sysAnnouncementSendMapper.getMyAnnouncementSendList(page, announcementSendModel));
	}

}
