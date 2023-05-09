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
 * @Description: 设备
 * @Author: smart-boot
 * @Date:   2019-11-07
 * @Version: V1.0
 */
@Data
@TableName("device_product")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="device_product对象", description="设备")
public class DeviceProduct {
    
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

	private java.lang.String deviceId;

	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
	private java.lang.String productName;
	/**设备级别*/
	@Excel(name = "设备级别", width = 15)
    @ApiModelProperty(value = "设备级别")
	private java.lang.String equipLevel;
	/**设备型号*/
	@Excel(name = "设备型号", width = 15)
    @ApiModelProperty(value = "设备型号")
	private java.lang.String productType;
	/**设备规格*/
	@Excel(name = "设备规格", width = 15)
    @ApiModelProperty(value = "设备规格")
	private java.lang.String specInfo;
	/**材质说明*/
	@Excel(name = "材质说明", width = 15)
    @ApiModelProperty(value = "材质说明")
	private java.lang.String materialType;
	/**生产厂家*/
	@Excel(name = "生产厂家", width = 15)
    @ApiModelProperty(value = "生产厂家")
	private java.lang.String maker;
	/**厂家编码*/
	@Excel(name = "厂家编码", width = 15)
    @ApiModelProperty(value = "厂家编码")
	private java.lang.String makerCode;
	/**投产日期*/
	@Excel(name = "投产日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "投产日期")
	private java.util.Date madeOn;
	/**计量单位*/
	@Excel(name = "计量单位", width = 15)
    @ApiModelProperty(value = "计量单位")
	private java.lang.String unit;
	/**长度*/
	@Excel(name = "长度", width = 15)
    @ApiModelProperty(value = "长度")
	private java.lang.Double length;
	/**宽度*/
	@Excel(name = "宽度", width = 15)
    @ApiModelProperty(value = "宽度")
	private java.lang.Double width;
	/**高度*/
	@Excel(name = "高度", width = 15)
    @ApiModelProperty(value = "高度")
	private java.lang.Double height;
	/**重量*/
	@Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
	private java.lang.Double weight;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
	private java.lang.String allunit;
	/**重量单位*/
	@Excel(name = "重量单位", width = 15)
    @ApiModelProperty(value = "重量单位")
	private java.lang.String weightunit;
	/**产品证书号*/
	@Excel(name = "产品证书号", width = 15)
    @ApiModelProperty(value = "产品证书号")
	private java.lang.String classCode;
	/**购买价格*/
	@Excel(name = "购买价格", width = 15)
    @ApiModelProperty(value = "购买价格")
	private java.lang.Double price;
	/**中文描述*/
	@Excel(name = "中文描述", width = 15)
    @ApiModelProperty(value = "中文描述")
	private java.lang.String descC;
	/**说明书*/
	@Excel(name = "说明书", width = 15)
    @ApiModelProperty(value = "说明书")
	private java.lang.String manualFile;
	/**品牌*/
	@Excel(name = "品牌", width = 15)
    @ApiModelProperty(value = "品牌")
	private java.lang.String brand;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
	private java.lang.Object memo;
	/**删除*/
	@Excel(name = "删除", width = 15)
    @ApiModelProperty(value = "删除")
	private java.lang.String delFlag;

}
