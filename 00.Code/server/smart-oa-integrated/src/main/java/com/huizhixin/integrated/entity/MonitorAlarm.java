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
 * @Description: 告警
 * @Author: smart-boot
 * @Date: 2020-01-06
 * @Version: V1.0
 */
@Data
@TableName("monitor_alarm")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "monitor_alarm对象", description = "告警")
public class MonitorAlarm {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**
     * companyId
     */
    @Excel(name = "companyId", width = 15)
    @ApiModelProperty(value = "companyId")
    private java.lang.String companyId;
    /**
     * positionName
     */
    @Excel(name = "positionName", width = 15)
    @ApiModelProperty(value = "positionName")
    private java.lang.String positionName;
    /**
     * 摄像机
     */
    @Excel(name = "摄像机", width = 15)
    @ApiModelProperty(value = "摄像机")
    private java.lang.Integer cameraId;
    /**
     * 通道号
     */
    @Excel(name = "通道号", width = 15)
    @ApiModelProperty(value = "通道号")
    private java.lang.String channelId;
    /**
     * 内容
     */
    @Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private java.lang.String content;
    /**
     * 视频id
     */
    @Excel(name = "视频id", width = 15)
    @ApiModelProperty(value = "视频id")
    private java.lang.String videoId;
    /**
     * delFlag
     */
    @Excel(name = "delFlag", width = 15)
    @ApiModelProperty(value = "delFlag")
    private java.lang.Integer delFlag;
    /**
     * createTime
     */
    @Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private java.util.Date createTime;

    private String remark;
}
