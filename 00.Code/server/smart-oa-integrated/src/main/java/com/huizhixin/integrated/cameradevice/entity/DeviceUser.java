package com.huizhixin.integrated.cameradevice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 设备用户信息
 * 
 * @author LongWeibing
 *
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class DeviceUser
{
    /**
     * Y user类型white(白名单),black（黑名单）
     */
    private String usertype;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * N 男, 女
     */
    private String sex;
    
    /**
     * N 民族
     */
    private String nation;
    
    /**
     * Y 身份证号
     */
    private String idno;
    
    /**
     * N 身份证有效期开始
     */
    private String idstartdate;
    
    /**
     * N 身份证有效期结束
     */
    private String idenddate;
    
    /**
     * N 名单有效期开始
     */
    private String peoplestartdate;
    
    /**
     * N 名单有效期结束
     */
    private String peopleenddate;
    
    /**
     * N 身份证签发机关
     */
    private String idissue;
    
    /**
     * N 身份证地址
     */
    private String idaddress;
    
    /**
     * ic卡号 20180716
     */
    private String icno;
    
    /**
     * 生日
     */
    private String birth;
    
    /**
     * 默认名单必须要带图片，设置false，则表示该名单可以不用带图片，进行录入；
     */
    private boolean passAlgo;
    
    /**
     * 0:任意时间识别成功都可同行 1：周一到周日指定时间段内识别成功才能通行，时间段外提示“无权限进入，请在指定时间段内识别”
     */
    private int timeGroupId;
    
    /**
     * 表示名单图片的base64字符串
     */
    private String picData1;
    
    /**
     * 
     */
    private String picData2;
    
    /**
     * 
     */
    private String picData3;
    
    private String picBase64;
    
    @Override
    public String toString()
    {
        return "DeviceUser [usertype=" + usertype + ", name=" + name + ", sex=" + sex + ", nation=" + nation + ", idno="
            + idno + ", idstartdate=" + idstartdate + ", idenddate=" + idenddate + ", peoplestartdate="
            + peoplestartdate + ", peopleenddate=" + peopleenddate + ", idissue=" + idissue + ", idaddress=" + idaddress
            + ", icno=" + icno + ", birth=" + birth + ", passAlgo=" + passAlgo + ", timeGroupId=" + timeGroupId
            + ", picData1=" + picData1 + ", picData2=" + picData2 + ", picData3=" + picData3 + ", picBase64="
            + picBase64 + "]";
    }
    
}
