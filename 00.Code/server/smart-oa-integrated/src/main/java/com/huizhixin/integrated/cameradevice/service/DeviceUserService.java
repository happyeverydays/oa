package com.huizhixin.integrated.cameradevice.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huizhixin.integrated.cameradevice.entity.CameraConfig;
import com.huizhixin.integrated.cameradevice.entity.DeviceUser;
import com.huizhixin.integrated.cameradevice.entity.ResultInfo;
import com.huizhixin.integrated.comm.HttpRequestUtils;


/**
 * 设备人员接口
 * 
 * @author LongWeibing
 * 
 */
@Service
public class DeviceUserService extends BaseClientService
{
    
    /**
     * 添加名单
     * 
     * @param userName
     * @param passWord
     * @return
     */
    public ResultInfo<Object> addDeviceWhiteList(String pass, int totalnum, int currentnum, DeviceUser deviceUser)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_ADD_WHITELIST;
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("pass", pass);
        jsonParam.put("totalnum", totalnum);
        jsonParam.put("currentnum", currentnum);
        jsonParam.put("data", deviceUser);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<Object> result = getResp(resultStr, Object.class);
        
        return result;
    }
    
    /**
     * 删除名称-根据类型、卡号
     * @param pass
     * @param parameter
     * @return
     */
    public ResultInfo<Object> deleteDeviceWhiteList(String pass, String idno, String userType)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_DELETE_WHITELIST;
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("pass", pass);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<Object> result = getResp(resultStr, Object.class);
        
        return result;
    }
    
    /**
     * 删除所有白名单
     * @param pass
     * @param parameter
     * @return
     */
    public ResultInfo<Object> deleteDeviceAllWhiteList(String pass)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_DELETE_ALL_WHITELIST;
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("pass", pass);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<Object> result = getResp(resultStr, Object.class);
        
        return result;
    }
    
    /**
     * 获取名单列表
     * @param pass
     * @param parameter
     * @return
     */
    public ResultInfo<DeviceUser> getAllDeviceIdWhiteList(String pass)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_GET_ALL_WHITELIST;
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("pass", pass);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<DeviceUser> result = getResp(resultStr, DeviceUser.class);
        
        return result;
    }
    
    /**
     * 获取名单列表
     * @param pass
     * @param parameter
     * @return
     */
    public ResultInfo<DeviceUser> getDeviceWhiteListDetailByIdNum(String pass, String idno)
    {
        String requestUrl = super.getHostUrl() + CameraConfig.PATH_GET_DETAIL_WHITELIST;
        
        JSONObject dataParam = new JSONObject();
        dataParam.put("idno", idno);
        
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("pass", pass);
        jsonParam.put("data", dataParam);
        
        String paramStr = jsonParam.toJSONString();
        String resultStr = HttpRequestUtils.httpPost(requestUrl, paramStr);
        // 构造返回信息
        ResultInfo<DeviceUser> result = getResp(resultStr, DeviceUser.class);
        
        return result;
    }
}
