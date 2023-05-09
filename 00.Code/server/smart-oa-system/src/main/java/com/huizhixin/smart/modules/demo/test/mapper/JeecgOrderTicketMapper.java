package com.huizhixin.smart.modules.demo.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huizhixin.smart.modules.demo.test.entity.JeecgOrderTicket;

/**
 * @Description: 订单机票
 * @Author:
 * @Date:  2019-02-15
 * @Version: V1.0
 */
public interface JeecgOrderTicketMapper extends BaseMapper<JeecgOrderTicket> {

	/**
	 *  通过主表外键批量删除客户
	 * @param mainId
	 * @return
	 */
    @Delete("DELETE FROM JEECG_ORDER_TICKET WHERE ORDER_ID = #{mainId}")
	public boolean deleteTicketsByMainId(String mainId);
    
    
    @Select("SELECT * FROM JEECG_ORDER_TICKET WHERE ORDER_ID = #{mainId}")
	public List<JeecgOrderTicket> selectTicketsByMainId(String mainId);
}
