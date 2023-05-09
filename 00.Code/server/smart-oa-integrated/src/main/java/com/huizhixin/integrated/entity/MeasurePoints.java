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
 * @Description: 测量点
 * @Author: smart-boot
 * @Date:   2019-11-07
 * @Version: V1.0
 */
@Data
@TableName("device_measure_points")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="device_measure_points对象", description="测量点")
public class MeasurePoints {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
	private java.lang.String sysOrgCode;
	/**设备产品ID*/
	@Excel(name = "设备产品ID", width = 15)
    @ApiModelProperty(value = "设备产品ID")
	private java.lang.String productId;
	/**观测点编号*/
	@Excel(name = "观测点编号", width = 15)
    @ApiModelProperty(value = "观测点编号")
	private java.lang.String pointNo;
	/**观测点*/
	@Excel(name = "观测点", width = 15)
    @ApiModelProperty(value = "观测点")
	private java.lang.String measureName;
	/**观测点类型*/
	@Excel(name = "观测点类型", width = 15)
    @ApiModelProperty(value = "观测点类型")
	private java.lang.Integer measureType;
	/**观测值类型*/
	@Excel(name = "观测值类型", width = 15)
    @ApiModelProperty(value = "观测值类型")
	private java.lang.Integer valueType;
	/**正常值*/
	@Excel(name = "正常值", width = 15)
    @ApiModelProperty(value = "正常值")
	private java.lang.String value;
	/**valueMin*/
	@Excel(name = "valueMin", width = 15)
    @ApiModelProperty(value = "valueMin")
	private java.lang.String valueMin;
	/**许可最大值*/
	@Excel(name = "许可最大值", width = 15)
    @ApiModelProperty(value = "许可最大值")
	private java.lang.String valueMax;
	/**参数单位*/
	@Excel(name = "参数单位", width = 15)
    @ApiModelProperty(value = "参数单位")
	private java.lang.String unit;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
	private java.lang.Integer status;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
	private java.lang.String memo;
	/**删除*/
	@Excel(name = "删除", width = 15)
    @ApiModelProperty(value = "删除")
	private java.lang.Integer delFlag;
	/**前置or测量点*/
	@Excel(name = "前置or测量点", width = 15)
    @ApiModelProperty(value = "前置or测量点")
	private java.lang.Integer type;

	private java.lang.String lastValue;

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date lastTime;
}
