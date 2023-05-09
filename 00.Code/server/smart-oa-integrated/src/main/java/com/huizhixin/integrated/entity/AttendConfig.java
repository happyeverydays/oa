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
 * @Description: 打卡配置
 * @Author: smart-boot
 * @Date:   2020-01-13
 * @Version: V1.0
 */
@Data
@TableName("attend_config")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="attend_config对象", description="打卡配置")
public class AttendConfig {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**班种*/
	@Excel(name = "班种", width = 15)
    @ApiModelProperty(value = "班种")
	private java.lang.String name;
	/**startTime*/
	@Excel(name = "startTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "startTime")
	private java.lang.String startTime;
	/**endTime*/
	@Excel(name = "endTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "endTime")
	private java.lang.String endTime;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
	private java.util.Date createTime;
}
