package com.huizhixin.integrated.cameradevice;

import java.util.Map;

import com.huizhixin.integrated.cameradevice.entity.DeviceParameter;
import com.huizhixin.integrated.cameradevice.entity.DeviceUser;
import com.huizhixin.integrated.cameradevice.entity.ResultInfo;
import com.huizhixin.integrated.cameradevice.service.DeviceBaseService;
import com.huizhixin.integrated.cameradevice.service.DeviceRunService;
import com.huizhixin.integrated.cameradevice.service.DeviceUserService;
import com.huizhixin.integrated.comm.ImageBase64Util;

public class MainTest
{
    static DeviceBaseService baseService = new DeviceBaseService();
    
    static DeviceRunService runService = new DeviceRunService();
    
    static DeviceUserService userService = new DeviceUserService();
    
    public static void main(String[] args)
    {
        // DeviceBaseService baseService = new DeviceBaseService();
        //
        // ResultInfo<Object> result = baseService.deviceLogin("admin", "888888");
        //
        // System.out.println(result.getResult());
        // System.out.println(result.getMessage());
        
//        setDeviceParameter();
//        getDeviceParameter();
        
        // addUserTest();
        // getAllDeviceIdWhiteList();
        getDeviceTime();
    }
    
    private static void getDeviceTime()
    {
        ResultInfo<Map> result = runService.getDeviceTime("888888");
        
        System.out.println(result.getResult());
        System.out.println(result.getMessage());
        
        System.out.println(result.getData());
    }
    
    private static void setDeviceParameter()
    {
        DeviceParameter parameter = new DeviceParameter();
        
        parameter.setPlatformEnable(0);
        parameter.setPlatformIp("http://192.168.0.130:8083/smart-boot/cameraListen/notify");
        
        ResultInfo<Object> result = runService.setDeviceParameter("888888", parameter);
        
        System.out.println(result.getResult());
        System.out.println(result.getMessage());
        
        System.out.println(result.getData());
    }
    
    private static void getDeviceParameter()
    {
        ResultInfo<DeviceParameter> result = runService.getDeviceParameter("888888");
        
        System.out.println(result.getResult());
        System.out.println(result.getMessage());
        
        System.out.println(result.getData());
    }
    
    private static void addUserTest()
    {
        DeviceUser deviceUser = new DeviceUser();
        
        deviceUser.setUsertype("white");
        deviceUser.setName("ALONG");
        deviceUser.setIdno("430224199902025569");
        deviceUser.setIcno("888888");
        deviceUser.setTimeGroupId(0);
        
        String imageUrl = "G:\\test\\ceshi.jpg";
        String picData1 = ImageBase64Util.getImageBase64(imageUrl);
        deviceUser.setPicData1(picData1);
        
        ResultInfo<Object> result = userService.addDeviceWhiteList("888888", 1, 1, deviceUser);
        
        System.out.println(result.getResult());
        System.out.println(result.getMessage());
    }
    
    private static void getAllDeviceIdWhiteList()
    {
        ResultInfo<DeviceUser> result = userService.getAllDeviceIdWhiteList("888888");
        
        System.out.println(result.getResult());
        System.out.println(result.getMessage());
        
        System.out.println(result.getData());
    }
}
