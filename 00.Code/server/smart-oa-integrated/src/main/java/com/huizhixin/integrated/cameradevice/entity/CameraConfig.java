package com.huizhixin.integrated.cameradevice.entity;

public interface CameraConfig
{
    public static String HOST_URL = "http://DEVICEIP:DEVICEPORT";
    
    /**
     * 设备登录
     */
    public static String PATH_DEVICELOGIN = "/deviceLogin";
    
    // ------------设备运行 begin--------------
    
    /**
     * 获取设备参数
     */
    public static String PATH_GET_PARAMETER = "/getDeviceParameter";
    
    /**
     * 设置设备参数
     */
    public static String PATH_SET_PARAMETER = "/setDeviceParameter";
    
    /**
     * 获取设备时间
     */
    public static String PATH_GET_DEVICETIME = "/getDeviceTime";
    
    /**
     * 设置设备时间
     */
    public static String PATH_SET_DEVICETIME = "/setDeviceTime";
    
    /**
     * 设备升级
     */
    public static String PATH_UPLOAD_UPGRADE = "/uploadUpgradeAPKFile";
    
    /**
     * 设备重启
     */
    public static String PATH_REBOOT_DEVICER = "/setDeviceReboot";
    
    /**
     * 设备控制开闸
     */
    public static String PATH_OPEN_DEVICE = "/setDeviceRemoteOpen";
    
    /**
     * 获取版本信息
     */
    public static String PATH_GET_VERSION = "/getDeviceVersion";
    
    /**
     * 获取相机当前图片
     */
    public static String PATH_GET_SNAPPICTURE = "/getDeviceSnapPicture";
    
    /**
     * 设置设备logo图片
     */
    public static String PATH_SET_LOGO = "/setDeviceLogo";
    
    /**
     * 恢复默认设备logo图片
     */
    public static String PATH_RESTORE_LOGO = "/restoreDeviceLogo";
    
    /**
     * 设置设备的网络地址
     */
    public static String PATH_SET_NETWORK = "/setDeviceNetwork";
    
    /**
     * 恢复设备所有参数配置
     */
    public static String PATH_RESTORE_PARAMETER = "/restoreDeviceDefaultParameter";
    
    /**
     * 设置设备固件升级
     */
    public static String PATH_OTA_UPGRADE = "/setDeviceOTAUpgrade";
    
    /**
     * 设置门禁时间组
     */
    public static String PATH_SET_TIME_GROUP = "/setDeviceTimeAccessGroups";
    
    // ------------设备运行 end--------------
    
    // ------------设备用户 begin--------------
    /**
     * 添加名单
     */
    public static String PATH_ADD_WHITELIST = "/addDeviceWhiteList";
    
    /**
     * 删除名单
     */
    public static String PATH_DELETE_WHITELIST = "/deleteDeviceWhiteList";
    
    /**
     * 删除所有白名单
     */
    public static String PATH_DELETE_ALL_WHITELIST = "/deleteDeviceAllWhiteList";
    
    /**
     * 获取名单列表
     */
    public static String PATH_GET_ALL_WHITELIST = "/getAllDeviceIdWhiteList";
    
    /**
     * 获取名单详情
     */
    public static String PATH_GET_DETAIL_WHITELIST = "/getDeviceWhiteListDetailByIdNum";
    // ------------设备用户 end--------------
    
    // ------------设备广告 begin--------------
    /**
     * 添加广告图片
     */
    public static String PATH_ADD_ADVERT = "/addDeviceAdvertPicture";
    
    /**
     * 删除所有广告图片
     */
    public static String PATH_DELETE_ALL_ADVERT = "/deleteAllDeviceAdvertPicture";
    
    // ------------设备运行 end--------------
    
}
