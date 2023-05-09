package com.huizhixin.integrated.cameradevice.entity;

import lombok.Data;

@Data
public class NotifyResult
{
    /**
     * uuid
     */
    private String id;
    
    /**
     * 比对时间 yyyy -MM - dd HH:mm:ss
     */
    private String time;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别
     */
    private String sex;
    
    /**
     * 民族
     */
    private String nation;
    
    /**
     * 身份证号
     */
    private String idNum;
    
    /**
     * 出生年月如：1997 年4月3日
     */
    private String birthday;
    
    /**
     * 身份证上住址信息
     */
    private String address;
    
    /**
     * 签发机关
     */
    private String depart;
    
    /**
     * 有效期开始
     */
    private String validStart;
    
    /**
     * 有效期结束
     */
    private String validEnd;
    
    /**
     * 设备mac
     */
    private String mac;
    
    /**
     * 出入标识 进入 出
     */
    private int inout;
    
    /**
     * 开门方式 白名单比对 人证比对 IC卡识别
     */
    private int openType;
    
    /**
     * 比对结果 比对成功 比对失败
     */
    private int resultStatus;
    
    /**
     * 抓拍照片 base64位
     */
    private String scenePhoto;
    
}
