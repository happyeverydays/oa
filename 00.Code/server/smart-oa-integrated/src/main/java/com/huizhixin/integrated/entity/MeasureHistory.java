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
 * @Description: 测量历史
 * @Author: smart-boot
 * @Date:   2019-11-07
 * @Version: V1.0
 */
@Data
@TableName("device_measure_history")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="device_measure_history对象", description="测量历史")
public class MeasureHistory {
    
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
	/**测量值*/
	@Excel(name = "测量值", width = 15)
    @ApiModelProperty(value = "测量值")
	private java.lang.String pointValue;
	/**测量id*/
	@Excel(name = "测量id", width = 15)
    @ApiModelProperty(value = "测量id")
	private java.lang.String measurmentId;
	/**pointNo*/
	@Excel(name = "pointNo", width = 15)
    @ApiModelProperty(value = "pointNo")
	private java.lang.String pointNo;
	/**measureName*/
	@Excel(name = "measureName", width = 15)
    @ApiModelProperty(value = "measureName")
	private java.lang.String measureName;
	/**measureType*/
	@Excel(name = "measureType", width = 15)
    @ApiModelProperty(value = "measureType")
	private java.lang.String measureType;
	/**valueType*/
	@Excel(name = "valueType", width = 15)
    @ApiModelProperty(value = "valueType")
	private java.lang.String valueType;
	/**valueMin*/
	@Excel(name = "valueMin", width = 15)
    @ApiModelProperty(value = "valueMin")
	private java.lang.String valueMin;
	/**valueMax*/
	@Excel(name = "valueMax", width = 15)
    @ApiModelProperty(value = "valueMax")
	private java.lang.String valueMax;
	/**unit*/
	@Excel(name = "unit", width = 15)
    @ApiModelProperty(value = "unit")
	private java.lang.String unit;
	/**status*/
	@Excel(name = "status", width = 15)
    @ApiModelProperty(value = "status")
	private java.lang.String status;
	/**memo*/
	@Excel(name = "memo", width = 15)
    @ApiModelProperty(value = "memo")
	private java.lang.String memo;
	/**productName*/
	@Excel(name = "productName", width = 15)
    @ApiModelProperty(value = "productName")
	private java.lang.String productName;

	/**创建日期*/
	@Excel(name = "测量时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "测量时间")
	private java.util.Date measureDt;

}
