package com.huizhixin.integrated.cameradevice.service;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.huizhixin.integrated.cameradevice.entity.ResultInfo;
import com.huizhixin.integrated.cameradevice.entity.resp.BaseRespMsg;

import scala.collection.mutable.StringBuilder;

public class BaseClientService
{
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    static
    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
        {
            @Override
            public void serialize(Object arg0, JsonGenerator jg, SerializerProvider arg2)
                throws IOException, JsonProcessingException
            {
                jg.writeString("");
            }
        });
    }
    
    protected String ipAddress;
    
    protected String port;
    
    /**
     * 设置设备地址
     * 
     * @param ipAddress
     * @param port
     */
    public void setHost(String ipAddress, String port)
    {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public String getHostUrl()
    {
        if (StringUtils.isEmpty(ipAddress))
        {
            ipAddress = "192.168.0.131";
        }
        if (StringUtils.isEmpty(port))
        {
            port = "9090";
        }
        
        StringBuilder hostUrlsb = new StringBuilder("http://").append(ipAddress).append(":").append(port);
        return hostUrlsb.toString();
    }
    
    
    public <T> ResultInfo<T> getResp(String jsonMsg, Class<T> valueType)
    {
        if (StringUtils.isEmpty(jsonMsg))
        {
            return null;
        }
        
        ResultInfo<T> msg = null;
        try
        {
            msg = objectMapper.readValue(jsonMsg, new TypeReference<ResultInfo<T>>(){});
        }
        catch (Exception e)
        {
            e.printStackTrace();
            msg = null;
        }
        
        return msg;
    }
    
}
