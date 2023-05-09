package com.huizhixin.integrated.cameradevice.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huizhixin.integrated.cameradevice.entity.CameraConfig;
import com.huizhixin.integrated.cameradevice.entity.ResultInfo;
import com.huizhixin.integrated.comm.HttpRequestUtils;


/**
 * 设备广告接口
 * 
 * @author LongWeibing
 * 
 */
@Service
public class DeviceAdvertService extends BaseClientService
{
    
    /**
     * 添加广告图片
     * 
     * @param userName
     * @param passWord
     * @return
     */
    public ResultInfo<Object> addDeviceAdvertPicture(String pass)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_DEVICELOGIN;
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("pass", pass);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<Object> result = getResp(resultStr, Object.class);
        
        return result;
    }
    
    /**
     * 删除所有广告图片
     * 
     * @param userName
     * @param passWord
     * @return
     */
    public ResultInfo<Object> deleteAllDeviceAdvertPicture(String pass)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_DEVICELOGIN;
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("pass", pass);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<Object> result = getResp(resultStr, Object.class);
        
        return result;
    }
}
