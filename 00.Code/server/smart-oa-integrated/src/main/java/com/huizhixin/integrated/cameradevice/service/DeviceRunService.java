package com.huizhixin.integrated.cameradevice.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huizhixin.integrated.cameradevice.entity.CameraConfig;
import com.huizhixin.integrated.cameradevice.entity.DeviceParameter;
import com.huizhixin.integrated.cameradevice.entity.ResultInfo;
import com.huizhixin.integrated.comm.HttpRequestUtils;

/**
 * 设备运行接口
 * 
 * @author LongWeibing
 * 
 */
@Service
public class DeviceRunService extends BaseClientService
{
    
    /**
     * 获取设备信息
     * 
     * @param userName
     * @param passWord
     * @return
     */
    public ResultInfo<DeviceParameter> getDeviceParameter(String pass)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_GET_PARAMETER;
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("pass", pass);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<DeviceParameter> result = getResp(resultStr, DeviceParameter.class);
        
        return result;
    }
    
    /**
     * 设置设备参数
     * @param pass
     * @param parameter
     * @return
     */
    public ResultInfo<Object> setDeviceParameter(String pass, DeviceParameter parameter)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_SET_PARAMETER;
        
        JSONObject jsonParam = new  JSONObject();
        jsonParam.put("pass", pass);
        jsonParam.put("data", parameter);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<Object> result = getResp(resultStr, Object.class);
        
        return result;
    }
    
    /**
     * 获取设备时间
     * 
     * @param userName
     * @param passWord
     * @return
     */
    public ResultInfo<Map> getDeviceTime(String pass)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_GET_DEVICETIME;
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("pass", pass);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<Map> result = getResp(resultStr, Map.class);
        
        return result;
    }
}
