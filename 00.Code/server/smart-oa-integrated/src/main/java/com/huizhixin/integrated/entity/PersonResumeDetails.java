package com.huizhixin.integrated.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.huizhixin.integrated.comm.LocalFileServerUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 面试详情
 * @Author: smart-boot
 * @Date:   2020-01-18
 * @Version: V1.0
 */
@Data
@TableName("person_resume_details")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="person_resume_details对象", description="面试详情")
public class PersonResumeDetails {
    
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
	/**面试评价*/
	@Excel(name = "面试评价", width = 15)
    @ApiModelProperty(value = "面试评价")
	private java.lang.String memo;
	/**面试官id*/
	@Excel(name = "面试官id", width = 15)
    @ApiModelProperty(value = "面试官id")
	private java.lang.String interviewerId;
	/**面试官工号*/
	@Excel(name = "面试官工号", width = 15)
    @ApiModelProperty(value = "面试官工号")
	private java.lang.String interviewerJobNumber;
	/**面试官姓名*/
	@Excel(name = "面试官姓名", width = 15)
    @ApiModelProperty(value = "面试官姓名")
	private java.lang.String interviewerName;
	/**面试时间*/
	@Excel(name = "面试时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "面试时间")
	private java.util.Date interviewerDate;

	private java.lang.String attachment;

	public String getAttachmentStr() {
		return LocalFileServerUtil.getWebUrl(this.attachment);
	}



}
