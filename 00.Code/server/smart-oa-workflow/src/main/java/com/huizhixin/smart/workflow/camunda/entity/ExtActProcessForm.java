package com.huizhixin.smart.workflow.camunda.entity;


import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 流程业务关联
 * @Author: smart-boot
 * @Date:   2020-02-06
 * @Version: V1.0
 */
@Data
@TableName("ext_act_process_form")
public class ExtActProcessForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private java.lang.String sysOrgCode;
	/**流程key*/
	@Excel(name = "流程key", width = 15)
	private java.lang.String processKey;
	/**唯一编码*/
	@Excel(name = "唯一编码", width = 15)
	private java.lang.String relationCode;
	/**表名/自定义表单code*/
	@Excel(name = "表名/自定义表单code", width = 15)
	private java.lang.String formTableName;
	/**表单类型*/
	@Excel(name = "表单类型", width = 15)
	private java.lang.String formType;
	/**表单处理样式*/
	@Excel(name = "表单处理样式", width = 15)
	private java.lang.String formDealStyle;
	/**业务表名字*/
	@Excel(name = "业务表名字", width = 15)
	private java.lang.String bizName;
	/**业务标题表达式*/
	@Excel(name = "业务标题表达式", width = 15)
	private java.lang.String titleExp;
	/**流程状态列名*/
	@Excel(name = "流程状态列名", width = 15)
	private java.lang.String flowStatusCol;
	/**表单地址*/
	@Excel(name = "表单地址", width = 15)
	private java.lang.String formKey;
	/**变量sql*/
	@Excel(name = "变量sql", width = 15)
	private java.lang.String varSql;
	/**变量接口*/
	@Excel(name = "变量接口", width = 15)
	private java.lang.String varInterface;
}
