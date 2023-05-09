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
 * @Description: json
 * @Author: smart-boot
 * @Date:   2019-12-27
 * @Version: V1.0
 */
@Data
@TableName("base_store_json")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="base_store_json对象", description="json")
public class StoreJson {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**name*/
	@Excel(name = "name", width = 15)
    @ApiModelProperty(value = "name")
	private java.lang.String name;
	/**desc*/
	@Excel(name = "desc", width = 15)
    @ApiModelProperty(value = "desc")
	private java.lang.String dsc;
	/**val*/
	@Excel(name = "val", width = 15)
    @ApiModelProperty(value = "val")
	private java.lang.Object val;
}
