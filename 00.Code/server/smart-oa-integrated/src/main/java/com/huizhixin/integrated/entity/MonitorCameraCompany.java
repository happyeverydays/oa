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
 * @Description: 分屏
 * @Author: smart-boot
 * @Date:   2020-01-08
 * @Version: V1.0
 */
@Data
@TableName("monitor_camera_company")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="monitor_camera_company对象", description="分屏")
public class MonitorCameraCompany {
    
	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
	private java.lang.Integer id;
	/**companyId*/
	@Excel(name = "companyId", width = 15)
    @ApiModelProperty(value = "companyId")
	private java.lang.String companyId;
	/**相机id*/
	@Excel(name = "相机id", width = 15)
    @ApiModelProperty(value = "相机id")
	private java.lang.String cameraId;
	/**相机名称*/
	@Excel(name = "相机名称", width = 15)
    @ApiModelProperty(value = "相机名称")
	private java.lang.String cname;
	/**cameraUrl*/
	@Excel(name = "cameraUrl", width = 15)
    @ApiModelProperty(value = "cameraUrl")
	private java.lang.String cameraUrl;
	/**类型 1、单分屏   2、4分屏 3、9分屏  4、16分屏*/
	@Excel(name = "类型 1、单分屏   2、4分屏 3、9分屏  4、16分屏", width = 15)
    @ApiModelProperty(value = "类型 1、单分屏   2、4分屏 3、9分屏  4、16分屏")
	private java.lang.Integer ctype;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
	private java.lang.Double sort;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
    @ApiModelProperty(value = "删除标识")
	private java.lang.Integer delFlag;
}
