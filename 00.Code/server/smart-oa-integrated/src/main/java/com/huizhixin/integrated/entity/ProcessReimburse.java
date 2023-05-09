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
 * @Description: 报销
 * @Author: smart-boot
 * @Date:   2020-01-15
 * @Version: V1.0
 */
@Data
@TableName("process_reimburse")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="process_reimburse对象", description="报销")
public class ProcessReimburse {
    
	/**主键*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**staffId*/
	@Excel(name = "staffId", width = 15)
    @ApiModelProperty(value = "staffId")
	private java.lang.String staffId;

	private String staffName;
	/**总价*/
	@Excel(name = "总价", width = 15)
    @ApiModelProperty(value = "总价")
	private java.lang.Double totalprice;
	/**原因*/
	@Excel(name = "原因", width = 15)
    @ApiModelProperty(value = "原因")
	private java.lang.String reason;
	/**申请时间*/
	@Excel(name = "申请时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "申请时间")
	private java.util.Date applyDate;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;

	@ApiModelProperty(value = "状态")
	private java.lang.String processState;

}
