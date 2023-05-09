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
 * @Description: 访客
 * @Author: jeecg-boot
 * @Date:   2019-08-31
 * @Version: V1.0
 */
@Data
@TableName("ios_visitor")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ios_visitor对象", description="访客")
public class Visitor {
    
	/**rowautoid*/
	@Excel(name = "rowautoid", width = 15)
    @ApiModelProperty(value = "rowautoid")
	private java.lang.Integer rowautoid;
	/**cardnumber*/
	@Excel(name = "cardnumber", width = 15)
    @ApiModelProperty(value = "cardnumber")
	private java.lang.String cardnumber;
	/**serialnumber*/
	@Excel(name = "serialnumber", width = 15)
    @ApiModelProperty(value = "serialnumber")
	private java.lang.String serialnumber;
	/**personid*/
	@Excel(name = "personid", width = 15)
    @ApiModelProperty(value = "personid")
	private java.lang.String personid;
	/**name*/
	@Excel(name = "name", width = 15)
    @ApiModelProperty(value = "name")
	private java.lang.String name;
	/**englishname*/
	@Excel(name = "englishname", width = 15)
    @ApiModelProperty(value = "englishname")
	private java.lang.String englishname;
	/**certificatenum*/
	@Excel(name = "certificatenum", width = 15)
    @ApiModelProperty(value = "certificatenum")
	private java.lang.String certificatenum;
	/**certificatetype*/
	@Excel(name = "certificatetype", width = 15)
    @ApiModelProperty(value = "certificatetype")
	private java.lang.String certificatetype;
	/**certificateposition*/
	@Excel(name = "certificateposition", width = 15)
    @ApiModelProperty(value = "certificateposition")
	private java.lang.String certificateposition;
	/**visitorname*/
	@Excel(name = "visitorname", width = 15)
    @ApiModelProperty(value = "visitorname")
	private java.lang.String visitorname;
	/**visitornamepinyin*/
	@Excel(name = "visitornamepinyin", width = 15)
    @ApiModelProperty(value = "visitornamepinyin")
	private java.lang.String visitornamepinyin;
	/**visitortype*/
	@Excel(name = "visitortype", width = 15)
    @ApiModelProperty(value = "visitortype")
	private java.lang.Integer visitortype;
	/**visitoraddress*/
	@Excel(name = "visitoraddress", width = 15)
    @ApiModelProperty(value = "visitoraddress")
	private java.lang.String visitoraddress;
	/**intime*/
	@Excel(name = "intime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "intime")
	private java.util.Date intime;
	/**inaddress*/
	@Excel(name = "inaddress", width = 15)
    @ApiModelProperty(value = "inaddress")
	private java.lang.String inaddress;
	/**outtime*/
	@Excel(name = "outtime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "outtime")
	private java.util.Date outtime;
	/**outaddress*/
	@Excel(name = "outaddress", width = 15)
    @ApiModelProperty(value = "outaddress")
	private java.lang.String outaddress;
	/**checkintime*/
	@Excel(name = "checkintime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "checkintime")
	private java.util.Date checkintime;
	/**starttime*/
	@Excel(name = "starttime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "starttime")
	private java.util.Date starttime;
	/**endtime*/
	@Excel(name = "endtime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "endtime")
	private java.util.Date endtime;
	/**enabletime*/
	@Excel(name = "enabletime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "enabletime")
	private java.util.Date enabletime;
	/**disabletime*/
	@Excel(name = "disabletime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "disabletime")
	private java.util.Date disabletime;
	/**invalidtime*/
	@Excel(name = "invalidtime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "invalidtime")
	private java.util.Date invalidtime;
	/**passstatus*/
	@Excel(name = "passstatus", width = 15)
    @ApiModelProperty(value = "passstatus")
	private java.lang.Integer passstatus;
	/**visitorcompany*/
	@Excel(name = "visitorcompany", width = 15)
    @ApiModelProperty(value = "visitorcompany")
	private java.lang.String visitorcompany;
	/**visitorcause*/
	@Excel(name = "visitorcause", width = 15)
    @ApiModelProperty(value = "visitorcause")
	private java.lang.String visitorcause;
	/**visitorcount*/
	@Excel(name = "visitorcount", width = 15)
    @ApiModelProperty(value = "visitorcount")
	private java.lang.Integer visitorcount;
	/**receptiondeptname*/
	@Excel(name = "receptiondeptname", width = 15)
    @ApiModelProperty(value = "receptiondeptname")
	private java.lang.String receptiondeptname;
	/**receptiondeptid*/
	@Excel(name = "receptiondeptid", width = 15)
    @ApiModelProperty(value = "receptiondeptid")
	private java.lang.String receptiondeptid;
	/**receptionpersonname*/
	@Excel(name = "receptionpersonname", width = 15)
    @ApiModelProperty(value = "receptionpersonname")
	private java.lang.String receptionpersonname;
	/**receptionpersonid*/
	@Excel(name = "receptionpersonid", width = 15)
    @ApiModelProperty(value = "receptionpersonid")
	private java.lang.String receptionpersonid;
	/**receptionpersonnamepinyin*/
	@Excel(name = "receptionpersonnamepinyin", width = 15)
    @ApiModelProperty(value = "receptionpersonnamepinyin")
	private java.lang.String receptionpersonnamepinyin;
	/**receptionpersonposition*/
	@Excel(name = "receptionpersonposition", width = 15)
    @ApiModelProperty(value = "receptionpersonposition")
	private java.lang.String receptionpersonposition;
	/**receptionpicture*/
	@Excel(name = "receptionpicture", width = 15)
    @ApiModelProperty(value = "receptionpicture")
	private java.lang.String receptionpicture;
	/**reserve1*/
	@Excel(name = "reserve1", width = 15)
    @ApiModelProperty(value = "reserve1")
	private java.lang.String reserve1;
	/**reserve2*/
	@Excel(name = "reserve2", width = 15)
    @ApiModelProperty(value = "reserve2")
	private java.lang.String reserve2;
	/**reserve3*/
	@Excel(name = "reserve3", width = 15)
    @ApiModelProperty(value = "reserve3")
	private java.lang.String reserve3;
	/**reserve4*/
	@Excel(name = "reserve4", width = 15)
    @ApiModelProperty(value = "reserve4")
	private java.lang.String reserve4;
	/**modifytime*/
	@Excel(name = "modifytime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "modifytime")
	private java.util.Date modifytime;
	/**operator*/
	@Excel(name = "operator", width = 15)
    @ApiModelProperty(value = "operator")
	private java.lang.String operator;

	private java.lang.String stockid;
	private java.lang.String stockname;

}
