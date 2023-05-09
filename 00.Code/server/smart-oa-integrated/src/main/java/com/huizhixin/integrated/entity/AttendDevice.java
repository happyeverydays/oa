package com.huizhixin.integrated.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 打卡设备
 * @Author: smart-boot
 * @Date:   2020-01-14
 * @Version: V1.0
 */
@Data
@TableName("attend_device")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="attend_device对象", description="打卡设备")
public class AttendDevice {

	/**主键*/
	@TableId(type = IdType.UUID)
	@ApiModelProperty(value = "主键")
	private java.lang.Integer id;
	/**名称*/
	@Excel(name = "名称", width = 15)
	@ApiModelProperty(value = "名称")
	private java.lang.String name;
	/**品牌*/
	@Excel(name = "品牌", width = 15)
	@ApiModelProperty(value = "品牌")
	private java.lang.String brand;
	/**生产厂商*/
	@Excel(name = "生产厂商", width = 15)
	@ApiModelProperty(value = "生产厂商")
	private java.lang.String generateVendor;
	/**启用时间*/
	@Excel(name = "启用时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "启用时间")
	private java.util.Date startTime;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "createTime")
	private java.util.Date createTime;
	/**楼层*/
	@Excel(name = "楼层", width = 15)
	@ApiModelProperty(value = "楼层")
	private java.lang.String flower;
	/**位置*/
	@Excel(name = "位置", width = 15)
	@ApiModelProperty(value = "位置")
	private java.lang.String location;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
	@ApiModelProperty(value = "用户名")
	private java.lang.String userName;
	/**密码*/
	@Excel(name = "密码", width = 15)
	@ApiModelProperty(value = "密码")
	private java.lang.String password;
	/**IP地址*/
	@Excel(name = "IP地址", width = 15)
	@ApiModelProperty(value = "IP地址")
	private java.lang.String ip;
	/**设备logo*/
	@Excel(name = "设备logo", width = 15)
	@ApiModelProperty(value = "设备logo")
	private java.lang.Integer port;
	/**dhcpFlag*/
	@Excel(name = "dhcpFlag", width = 15)
	@ApiModelProperty(value = "dhcpFlag")
	private java.lang.Integer dhcpFlag;
	/** 默认网关 */
	@Excel(name = " 默认网关 ", width = 15)
	@ApiModelProperty(value = " 默认网关 ")
	private java.lang.String gateway;
	/**子网掩码*/
	@Excel(name = "子网掩码", width = 15)
	@ApiModelProperty(value = "子网掩码")
	private java.lang.String mask;
	/**设备logo*/
	@Excel(name = "设备logo", width = 15)
	@ApiModelProperty(value = "设备logo")
	private java.lang.String logo;
}
