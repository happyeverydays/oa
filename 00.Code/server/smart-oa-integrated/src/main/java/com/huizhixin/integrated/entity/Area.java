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
 * @Description: 区域
 * @Author: jeecg-boot
 * @Date:   2019-09-09
 * @Version: V1.0
 */
@Data
@TableName("base_area")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="base_area对象", description="区域")
public class Area {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**companyid*/
	@Excel(name = "companyid", width = 15)
    @ApiModelProperty(value = "companyid")
	private java.lang.String companyId;
	/**parentid*/
	@Excel(name = "parentid", width = 15)
    @ApiModelProperty(value = "parentId")
	private java.lang.Integer parentId;
	/**type*/
	@Excel(name = "type", width = 15)
    @ApiModelProperty(value = "type")
	private java.lang.Integer type;
	/**level*/
	@Excel(name = "level", width = 15)
    @ApiModelProperty(value = "level")
	private java.lang.Integer level;
	/**location*/
	@Excel(name = "location", width = 15)
    @ApiModelProperty(value = "location")
	private java.lang.String location;
	/**gpslati*/
	@Excel(name = "gpslati", width = 15)
    @ApiModelProperty(value = "gpslati")
	private java.lang.String gpslati;
	/**gpslongi*/
	@Excel(name = "gpslongi", width = 15)
    @ApiModelProperty(value = "gpslongi")
	private java.lang.String gpslongi;

	private java.lang.String name;
	/**delflag*/
	@Excel(name = "delflag", width = 15)
    @ApiModelProperty(value = "delflag")
	private java.lang.Integer delflag;
	/**createtime*/
	@Excel(name = "createtime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createtime")
	private java.util.Date createtime;
}
