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
 * @Description: 三维管理
 * @Author: smart-boot
 * @Date: 2019-12-12
 * @Version: V1.0
 */
@Data
@TableName("obj_model")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "obj_model对象", description = "三维管理")
public class ObjModel {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**
     * 名称
     */
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
    /**
     * rotateActive
     */
    @Excel(name = "rotateActive", width = 15)
    @ApiModelProperty(value = "rotateActive")
    private java.lang.Integer rotateActive;

    private java.lang.Integer fov;

    /**
     * angleX
     */
    @Excel(name = "angleX", width = 15)
    @ApiModelProperty(value = "angleX")
    private java.lang.Double angleX;

    private String quaternion;


    /**
     * angleY
     */
    @Excel(name = "angleY", width = 15)
    @ApiModelProperty(value = "angleY")
    private java.lang.Double angleY;
    /**
     * angleZ
     */
    @Excel(name = "angleZ", width = 15)
    @ApiModelProperty(value = "angleZ")
    private java.lang.Double angleZ;
    /**
     * 方位路径，需要唯一
     */
    @Excel(name = "方位路径，需要唯一", width = 15)
    @ApiModelProperty(value = "方位路径，需要唯一")
    private java.lang.String path;
    /**
     * backgroundImg
     */
    @Excel(name = "backgroundImg", width = 15)
    @ApiModelProperty(value = "backgroundImg")
    private java.lang.String backgroundImg;
    /**
     * backgroundColor
     */
    @Excel(name = "backgroundColor", width = 15)
    @ApiModelProperty(value = "backgroundColor")
    private java.lang.String backgroundColor;
    /**
     * objFile
     */
    @Excel(name = "objFile", width = 15)
    @ApiModelProperty(value = "objFile")
    private java.lang.String objFile;
    /**
     * createTime
     */
    @Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private java.util.Date createTime;

    private Integer isMovable;

    private String chartletPackage;

    private String mtlUrl;

    private String selectedColor;

    public String getMtlUrlStr() {
        return LocalFileServerUtil.getWebUrl(this.mtlUrl);
    }

    public java.lang.String getBackgroundImgStr() {
        return LocalFileServerUtil.getWebUrl(backgroundImg);
    }

    public java.lang.String getObjFileStr() {
        return LocalFileServerUtil.getWebUrl(objFile);
    }


}
