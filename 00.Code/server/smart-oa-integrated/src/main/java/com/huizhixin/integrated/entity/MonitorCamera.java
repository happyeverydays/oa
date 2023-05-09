package com.huizhixin.integrated.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huizhixin.smart.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 监控
 * @Author: jeecg-boot
 * @Date: 2019-08-27
 * @Version: V1.0
 */
@Data
@TableName("monitor_camera")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "monitor_camera对象", description = "监控")
public class MonitorCamera {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * companyid
     */
    @Excel(name = "companyId", width = 15)
    @ApiModelProperty(value = "companyId")
    private String companyId;

    /**
     * 所在区域
     */
    @Excel(name = "所在区域", width = 15)
    @ApiModelProperty(value = "所在区域")
    private Integer areaId;

    /**
     * 通道号
     */
    @Excel(name = "通道号", width = 15)
    @ApiModelProperty(value = "通道号")
    private Integer channelid;

    /**
     * 监控名称
     */
    @Excel(name = "监控名称", width = 15)
    @ApiModelProperty(value = "监控名称")
    private String name;

    /**
     * 生产厂商
     */
    @Excel(name = "生产厂商", width = 15)
    @ApiModelProperty(value = "生产厂商")
    private String manufacturer;

    /**
     * 地址
     */
    @Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private String url;

    /**
     * 地址
     */
    @Excel(name = "id名称", width = 15)
    @ApiModelProperty(value = "id名称")
    private String channelidname;

    /**
     * delflag
     */
    @Excel(name = "delflag", width = 15)
    @ApiModelProperty(value = "delflag")
    private Integer delflag;

    /**
     * delflag
     */
    @Excel(name = "status", width = 15)
    @ApiModelProperty(value = "status")
    private Integer status;

    /**
     * createtime
     */
    @Excel(name = "createtime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createtime")
    private Date createTime;

    /**
     * delflag
     */
    @Excel(name = "model3dno", width = 15)
    @ApiModelProperty(value = "model3dno")
    private String model3dno;

    @Excel(name = "人脸识别通道号", width = 15)
    @ApiModelProperty(value = "distchannel")
    private String distchannel;

    @TableField(exist = false)
    private String areaname;

    @Excel(name = "ip地址", width = 15)
    @ApiModelProperty(value = "ipaddress")
    private String ipaddress;

    @Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "username")
    private String username;

    @Excel(name = "密码", width = 15)
    @ApiModelProperty(value = "password")
    private String password;

    @Excel(name = "端口号", width = 15)
    @ApiModelProperty(value = "port")
    private Integer port;

    @Excel(name = "摄像机类型", width = 15,dicCode="cameratype")
    @ApiModelProperty(value = "cameratype")
    private Integer cameratype;

}
