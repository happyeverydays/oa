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
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2019-09-11
 * @Version: V1.0
 */
@Data
@TableName("monitor_area_permission")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="monitor_area_permission对象", description="1")
public class AreaPermission {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**arearid*/
	@Excel(name = "arearid", width = 15)
    @ApiModelProperty(value = "arearid")
	private java.lang.Integer arearid;
	/**type*/
	@Excel(name = "type", width = 15)
    @ApiModelProperty(value = "type")
	private java.lang.Integer type;
	/**userid*/
	@Excel(name = "userid", width = 15)
    @ApiModelProperty(value = "userid")
	private java.lang.String userid;
	/**createtime*/
	@Excel(name = "createtime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createtime")
	private java.util.Date createtime;
}
