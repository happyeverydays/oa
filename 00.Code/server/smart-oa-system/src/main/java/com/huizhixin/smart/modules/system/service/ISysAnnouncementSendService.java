package com.huizhixin.smart.modules.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.smart.modules.system.entity.SysAnnouncementSend;
import com.huizhixin.smart.modules.system.model.AnnouncementSendModel;

/**
 * @Description: 用户通告阅读标记表
 * @Author:
 * @Date:  2019-02-21
 * @Version: V1.0
 */
public interface ISysAnnouncementSendService extends IService<SysAnnouncementSend> {

	public List<String> queryByUserId(String userId);
	
	/**
	 * @功能：获取我的消息
	 * @param announcementSendModel
	 * @return
	 */
	public Page<AnnouncementSendModel> getMyAnnouncementSendPage(Page<AnnouncementSendModel> page,AnnouncementSendModel announcementSendModel);

}
