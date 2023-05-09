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
 * @Description: 简历管理
 * @Author: smart-boot
 * @Date:   2020-01-18
 * @Version: V1.0
 */
@Data
@TableName("person_resume")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="person_resume对象", description="简历管理")
public class PersonResume {
    
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
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
	private java.lang.String username;
	/**学历*/
	@Excel(name = "学历", width = 15)
    @ApiModelProperty(value = "学历")
	private java.lang.String education;
	/**工作年限*/
	@Excel(name = "工作年限", width = 15)
    @ApiModelProperty(value = "工作年限")
	private java.lang.String workYear;
	/**推荐人*/
	@Excel(name = "推荐人", width = 15)
    @ApiModelProperty(value = "推荐人")
	private java.lang.String recomendPerson;
	/**面试官*/
	@Excel(name = "面试官", width = 15)
    @ApiModelProperty(value = "面试官")
	private java.lang.String interviewer;
	/**面试流程*/
	@Excel(name = "面试流程", width = 15)
    @ApiModelProperty(value = "面试流程")
	private java.lang.String interviewState;
}
