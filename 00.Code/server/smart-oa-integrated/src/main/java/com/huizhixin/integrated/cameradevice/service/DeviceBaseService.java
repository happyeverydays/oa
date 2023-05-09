package com.huizhixin.integrated.cameradevice.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huizhixin.integrated.cameradevice.entity.CameraConfig;
import com.huizhixin.integrated.cameradevice.entity.ResultInfo;
import com.huizhixin.integrated.comm.HttpRequestUtils;


/**
 * 设备基础接口
 * 
 * @author LongWeibing
 * 
 */
@Service
public class DeviceBaseService extends BaseClientService
{
    
    /**
     * 设备登录
     * 
     * @param userName
     * @param passWord
     * @return
     */
    public ResultInfo<Object> deviceLogin(String userName, String passWord)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_DEVICELOGIN;
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userName", userName);
        jsonParam.put("passWord", passWord);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<Object> result = getResp(resultStr, Object.class);
        
        return result;
    }
}
