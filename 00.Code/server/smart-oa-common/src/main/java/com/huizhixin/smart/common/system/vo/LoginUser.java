package com.huizhixin.smart.common.system.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 在线用户信息
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 登录人id
	 */
	private String id;

	/**
	 * 登录人账号
	 */
	private String username;

	/**
	 * 登录人名字
	 */
	private String realname;

	/**
	 * 登录人密码
	 */
	private String password;

     /**
      * 当前登录部门code
      */
    private String orgCode;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 生日
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	/**
	 * 性别（1：男 2：女）
	 */
	private Integer sex;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 状态(1：正常 2：冻结 ）
	 */
	private Integer status;
	
	private String delFlag;
	/**
     * 同步工作流引擎1同步0不同步
     */
    private String activitiSync;
    
    private String sysCompanyId="123"; //多租户的id标识。
    
    
    private String roleCode;
	private List<String> roleCodeList;
	private String departId;
	private List<String> departIdList;

}
