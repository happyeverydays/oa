package com.huizhixin.integrated.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.huizhixin.integrated.comm.LocalFileServerUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 用户
 * @Author: jeecg-boot
 * @Date: 2019-08-24
 * @Version: V1.0
 */
@Data
@TableName("base_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "base_user对象", description = "用户")
public class User {


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**
     * companyid
     */
    @Excel(name = "companyid", width = 15)
    @ApiModelProperty(value = "companyid")
    private java.lang.Integer companyid;

    private Date birthday;
    /**
     * 1-员工；2-外协；3-临时
     */
    @Excel(name = "1-员工；2-外协；3-临时", width = 15)
    @ApiModelProperty(value = "1-员工；2-外协；3-临时")
    private java.lang.Integer type;
    /**
     * 门禁卡卡号
     */
    @Excel(name = "门禁卡卡号", width = 15)
    @ApiModelProperty(value = "门禁卡卡号")
    private java.lang.String cardno;
    /**
     * 工号
     */
    @Excel(name = "工号", width = 15)
    @ApiModelProperty(value = "工号")
    private java.lang.String jobnumber;
    /**
     * name
     */
    @Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "name")
    private java.lang.String name;
    /**
     * gender
     */
    @Excel(name = "性别", width = 15,replace = {"男_1","女_2"})
    @ApiModelProperty(value = "gender")
    private java.lang.Integer gender;
    /**
     * phone
     */
    @Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "phone")
    private java.lang.String phone;
    /**
     * idcard
     */
    @Excel(name = "idcard", width = 15)
    @ApiModelProperty(value = "idcard")
    private java.lang.String idcard;
    /**
     * departmentid
     */
    @Excel(name = "departmentid", width = 15)
    @ApiModelProperty(value = "departmentid")
    private java.lang.String departmentid;
    /**
     * positionid
     */
    @Excel(name = "positionid", width = 15)
    @ApiModelProperty(value = "positionid")
    private java.lang.String positionid;
    /**
     * education
     */
    @Excel(name = "education", width = 15)
    @ApiModelProperty(value = "education")
    private java.lang.String education;
    /**
     * 照片
     */
    @Excel(name = "照片", width = 15)
    @ApiModelProperty(value = "照片")
    private java.lang.String portrait;

    public  String getportraitPath() {
        if(portrait==null){
            return  null;
        }
        return LocalFileServerUtil.getDir() + portrait;
    }

    /**
     * 状态
     * 1-在岗；2-离职
     */
    @Excel(name = "状态	1-在岗；2-离职", width = 15)
    @ApiModelProperty(value = "状态	1-在岗；2-离职")
    private java.lang.Integer status;
    /**
     * delflag
     */
    @Excel(name = "delflag", width = 15)
    @ApiModelProperty(value = "delflag")
    private java.lang.Integer delflag;
    /**
     * createtime
     */
    @Excel(name = "createtime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createtime")
    private java.util.Date createtime;

    private String szid;

    // 部门
    @Excel(name = "部门", width = 15)
    private String departname;

    private String stockid;
    private String stockname;
}
