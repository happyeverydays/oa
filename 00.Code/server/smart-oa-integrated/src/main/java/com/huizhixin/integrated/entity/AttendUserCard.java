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
 * @Description: 发卡管理
 * @Author: smart-boot
 * @Date:   2020-01-14
 * @Version: V1.0
 */
@Data
@TableName("attend_user_card")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="attend_user_card对象", description="发卡管理")
public class AttendUserCard {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**userId*/
	@Excel(name = "userId", width = 15)
    @ApiModelProperty(value = "userId")
	private java.lang.String userId;
	/**userName*/
	@Excel(name = "userName", width = 15)
    @ApiModelProperty(value = "userName")
	private java.lang.String userName;
	/**cardNo*/
	@Excel(name = "cardNo", width = 15)
    @ApiModelProperty(value = "cardNo")
	private java.lang.String cardNo;
	/**type*/
	@Excel(name = "type", width = 15)
    @ApiModelProperty(value = "type")
	private java.lang.String type;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
	private java.util.Date createTime;
}
