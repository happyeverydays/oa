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
 * @Description: 三维管理明细
 * @Author: smart-boot
 * @Date: 2019-12-12
 * @Version: V1.0
 */
@Data
@TableName("obj_model_detail")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "obj_model_detail对象", description = "三维管理明细")
public class ObjModelDetail {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**
     * objId
     */
    @Excel(name = "objId", width = 15)
    @ApiModelProperty(value = "objId")
    private java.lang.Integer objId;
    /**
     * 设备编码
     */
    @Excel(name = "设备编码", width = 15)
    @ApiModelProperty(value = "设备编码")
    private java.lang.String deviceCode;
    /**
     * 设备名称
     */
    @Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private java.lang.String deviceName;
    /**
     * compPic
     */
    @Excel(name = "compPic", width = 15)
    @ApiModelProperty(value = "compPic")
    private java.lang.String compPic;
    /**
     * pic
     */
    @Excel(name = "pic", width = 15)
    @ApiModelProperty(value = "pic")
    private java.lang.String pic;
    /**
     * 1、激活 0、未激活
     */
    @Excel(name = "1、激活 0、未激活", width = 15)
    @ApiModelProperty(value = "1、激活 0、未激活")
    private java.lang.Integer isActive;
    /**
     * 组件的id
     */
    @Excel(name = "组件的id", width = 15)
    @ApiModelProperty(value = "组件的id")
    private java.lang.String modelId;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    public String getComPicStr() {
        return LocalFileServerUtil.getWebUrl(this.compPic);
    }


}
