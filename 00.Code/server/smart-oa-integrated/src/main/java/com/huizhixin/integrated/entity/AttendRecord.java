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
 * @Description: 考勤记录
 * @Author: smart-boot
 * @Date:   2020-01-13
 * @Version: V1.0
 */
@Data
@TableName("attend_record")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="attend_record对象", description="考勤记录")
public class AttendRecord {

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;

	@Excel(name = "部门", width = 15)
	@ApiModelProperty(value = "部门")
	private java.lang.String deptName;

	@ApiModelProperty(value = "部门id")
	private java.lang.String deptId;
	/**打卡设备id*/
	@Excel(name = "打卡设备id", width = 15)
    @ApiModelProperty(value = "打卡设备id")
	private java.lang.String deviceId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
	private java.lang.String userid;

	@Excel(name = "工号", width = 15)
	private java.lang.String jobNumber;

	/**username*/
	@Excel(name = "username", width = 15)
    @ApiModelProperty(value = "username")
	private java.lang.String username;
	/**startTime*/
	@Excel(name = "startTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "startTime")
	private java.util.Date startTime;
	/**1、指纹  2、刷卡 3、人脸*/
	@Excel(name = "1、指纹  2、刷卡 3、人脸", width = 15)
    @ApiModelProperty(value = "1、指纹  2、刷卡 3、人脸")
	private java.lang.String attendWay;

	@Excel(name = "打卡班次", width = 15)
	@ApiModelProperty(value = "打卡班次")
	private java.lang.String attendType;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
	private java.lang.String memo;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
	private java.util.Date createTime;
	/**地点*/
	@Excel(name = "地点", width = 15)
    @ApiModelProperty(value = "地点")
	private java.lang.String location;


}
