package com.huizhixin.integrated.cameradevice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 设备参数信息
 * 
 * @author LongWeibing
 *
 */
@Data
@JsonInclude(Include.NON_EMPTY) 
public class DeviceParameter
{
    private String name;
    
    /**
     * 位置
     */
    private String location;
    
    /**
     * 出入 0出口 1入口
     */
    private Integer inout;
    
    /**
     * 上传黑名单记录 0不上传 1上传
     */
    private Integer upblack;
    
    /**
     * 上传全景图片 0上传 1不上传
     */
    private Integer uploadImage;
    
    /**
     * 图片质量 [0,5] 对应[100%,50%]
     */
    private Integer imageQuality;
    
    /**
     * 密码 6位数字
     */
    private String pwd;
    
    /**
     * 识别分值
     */
    private Integer whitevalue;
    
    /**
     * 人证识别阈值 [0,99]
     */
    private Integer mapvalue;
    
    /**
     * 单人识别间隔 秒
     */
    private Integer recogSpaceTime;
    
    /**
     * 开门延迟 秒
     */
    private Integer delayvalue;
    
    /**
     * 广告标语
     */
    private String banner;
    
    /**
     * 广告切换时间 秒
     */
    private Integer bannerSpaceTime;
    
    /**
     * 广告开启 0开 1关
     */
    private Integer bannerEnable;
    
    /**
     * 全景图片存储
     */
    private Integer imageSaveEnable;
    
    /**
     * 人脸检测语音
     */
    private Integer detectVoiceEnable;
    
    /**
     * 补光灯亮度
     */
    private Integer lightLevelPercent;
    
    /**
     * 0 常关 1 常开 2 时间段 3自动补光
     */
    private Integer lightType;
    
    /**
     * 开始时间
     */
    private String lightTimeStart;
    
    /**
     * 结束时间
     */
    private String lightTimeEnd;
    
    /**
     * 系统音量 [0,7]
     */
    private Integer systemVol;
    
    /**
     * 识别距离 [0,5] [无限制,0.5米,1,2,3,4]
     */
    private Integer detectRange;
    
    /**
     * 上传识别记录到平台 0开 1关
     */
    private Integer platformEnable;
    
    /**
     * 平台地址
     */
    private String platformIp;
    
    /**
     * 韦根输出类型 0.韦根26 1.韦根34 2.韦根66
     */
    private Integer wgOutType;
    
    /**
     * 继电器常开,常关
     */
    private Integer relayMode;
    
    /**
     * 活体开关
     */
    private Integer livenessEnable;
    
    /**
     * 活体分数
     */
    private Integer livenessValue;
    
    /**
     * 识别结果 姓名加密 0开1关
     */
    private Integer resultNameEncrypt;
    
    /**
     * 识别模式 0关 1 开
     */
    private Integer recogModeDB;
    
    /**
     * 识别模式 0关 1 ic刷卡 2 ic 识别
     */
    private Integer recogModeIC;
    
    /**
     * 识别模式 0关 1 人证识别 2人证库识别
     */
    private Integer recogModeID;
    
    /**
     * 识别开门 0继电器输出 1继电器不输出
     */
    private Integer recogRelay;
    
    /**
     * 自定义识别结果显示 "{name}您好!",
     */
    private String resultFormat;
    
    /**
     * 自定义语音内容 "{name}欢迎光临"
     */
    private String resultVoiceFormat;
    
    /**
     * 重启间隔 "DDHHmm"
     */
    private String rebootTime;
    
    /**
     * 语音设置 [0,2] [默认,播报名字,自定义]
     */
    private Integer voiceSetting;
    
    /**
     * 显示设置 [0,1] [名字,自定义]
     */
    private Integer resultSetting;
    
    /**
     * 上传心跳到指定的地址 0开 1关
     */
    private Integer heartBeatEnable;
    
    /**
     * 心跳的地址
     */
    private String heartBeatIp;
    
    @Override
    public String toString()
    {
        return "DeviceParameter [name=" + name + ", location=" + location + ", inout=" + inout + ", upblack=" + upblack
            + ", uploadImage=" + uploadImage + ", imageQuality=" + imageQuality + ", pwd=" + pwd + ", whitevalue="
            + whitevalue + ", mapvalue=" + mapvalue + ", recogSpaceTime=" + recogSpaceTime + ", delayvalue="
            + delayvalue + ", banner=" + banner + ", bannerSpaceTime=" + bannerSpaceTime + ", bannerEnable="
            + bannerEnable + ", imageSaveEnable=" + imageSaveEnable + ", detectVoiceEnable=" + detectVoiceEnable
            + ", lightLevelPercent=" + lightLevelPercent + ", lightType=" + lightType + ", lightTimeStart="
            + lightTimeStart + ", lightTimeEnd=" + lightTimeEnd + ", systemVol=" + systemVol + ", detectRange="
            + detectRange + ", platformEnable=" + platformEnable + ", platformIp=" + platformIp + ", wgOutType="
            + wgOutType + ", relayMode=" + relayMode + ", livenessEnable=" + livenessEnable + ", livenessValue="
            + livenessValue + ", resultNameEncrypt=" + resultNameEncrypt + ", recogModeDB=" + recogModeDB
            + ", recogModeIC=" + recogModeIC + ", recogModeID=" + recogModeID + ", recogRelay=" + recogRelay
            + ", resultFormat=" + resultFormat + ", resultVoiceFormat=" + resultVoiceFormat + ", rebootTime="
            + rebootTime + ", voiceSetting=" + voiceSetting + ", resultSetting=" + resultSetting + ", heartBeatEnable="
            + heartBeatEnable + ", heartBeatIp=" + heartBeatIp + "]";
    }
    
}
