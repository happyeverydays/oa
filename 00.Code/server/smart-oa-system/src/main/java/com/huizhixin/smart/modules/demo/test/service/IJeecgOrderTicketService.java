package com.huizhixin.smart.modules.demo.test.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.smart.modules.demo.test.entity.JeecgOrderTicket;

/**
 * @Description: 订单机票
 * @Author:
 * @Date:  2019-02-15
 * @Version: V1.0
 */
public interface IJeecgOrderTicketService extends IService<JeecgOrderTicket> {
	
	public List<JeecgOrderTicket> selectTicketsByMainId(String mainId);
}
