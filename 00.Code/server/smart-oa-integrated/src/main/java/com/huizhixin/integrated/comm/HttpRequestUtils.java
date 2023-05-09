package com.huizhixin.integrated.comm;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class HttpRequestUtils
{
    
    // private static Log log = LogManager.getLog();
    
    /**
     * httpPost
     * 
     * @param url 路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPostJosn(String url, String paramStr)
    {
        return httpPost(url, paramStr, false);
    }
    
    /**
     * post请求
     * 
     * @param url url地址
     * @param jsonParam 参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url, String paramStr, boolean noNeedResponse)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try
        {
            if (null != paramStr)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(paramStr, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /** 请求发送成功，并得到响应 **/
            if (result.getStatusLine().getStatusCode() == 200)
            {
                String str = "";
                try
                {
                    /** 读取服务器返回过来的json字符串数据 **/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse)
                    {
                        return null;
                    }
                    /** 把json字符串转换成json对象 **/
                    jsonResult = JSONObject.fromObject(str);
                }
                catch (Exception e)
                {
                    // log.error("Query object status by parent failded.{}", e);
                    // logger.error("post请求提交失败:" + url, e);
                }
            }
            else
            {
                // log.error("Parent server faulty, statusCode={}.", result.getStatusLine().getStatusCode());
                
            }
        }
        catch (IOException e)
        {
            // log.error("Query object status by parent failded.{}", e);
            
        }
        return jsonResult;
    }
    
    /**
     * post请求
     * 
     * @param url url地址
     * @param jsonParam 参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public static String httpPost(String url, String paramStr)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String strResult = null;
        HttpPost method = new HttpPost(url);
        try
        {
            if (null != paramStr)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(paramStr, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /** 请求发送成功，并得到响应 **/
            if (result.getStatusLine().getStatusCode() == 200)
            {
                try
                {
                    /** 读取服务器返回过来的json字符串数据 **/
                    strResult = EntityUtils.toString(result.getEntity());
//                    if (noNeedResponse)
//                    {
//                        return null;
//                    }
                }
                catch (Exception e)
                {
                    // log.error("Query object status by parent failded.{}", e);
                    // logger.error("post请求提交失败:" + url, e);
                }
            }
            else
            {
                // log.error("Parent server faulty, statusCode={}.", result.getStatusLine().getStatusCode());
                
            }
        }
        catch (IOException e)
        {
            // log.error("Query object status by parent failded.{}", e);
        }
        return strResult;
    }
    
    /**
     * post请求
     * 
     * @param url url地址
     * @param jsonParam 参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public static String httpPut(String url, String paramStr, Map<String, String> headerMap, boolean noNeedResponse)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String strResult = null;
        HttpPut method = new HttpPut(url);
        
        if (headerMap != null)
        {
            for (String key : headerMap.keySet())
            {
                method.addHeader(key, headerMap.get(key));
            }
        }
        
        try
        {
            if (null != paramStr)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(paramStr, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /** 请求发送成功，并得到响应 **/
            System.out.println(result.getStatusLine().getStatusCode());
            if (result.getStatusLine().getStatusCode() == 200)
            {
                try
                {
                    /** 读取服务器返回过来的json字符串数据 **/
                    strResult = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse)
                    {
                        return null;
                    }
                    /** 把json字符串转换成json对象 **/
                    //jsonResult = JSONObject.fromObject(str);
                }
                catch (Exception e)
                {
                    // log.error("Query object status by parent failded.{}", e);
                    // logger.error("post请求提交失败:" + url, e);
                }
            }
            else
            {
                // log.error("Parent server faulty, statusCode={}.", result.getStatusLine().getStatusCode());
            }
        }
        catch (IOException e)
        {
            // log.error("Query object status by parent failded.{}", e);
            
        }
        return strResult;
    }
    
    /**
     * post请求
     * 
     * @param url url地址
     * @param jsonParam 参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public static String httpGet(String url, String paramStr, Map<String, String> headerMap, boolean noNeedResponse)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //JSONObject jsonResult = null;
        String resultStr = null;
        HttpGet method = new HttpGet(url);
        
        if (headerMap != null)
        {
            for (String key : headerMap.keySet())
            {
                method.addHeader(key, headerMap.get(key));
            }
        }
        
        try
        {
            // if (null != paramStr)
            // {
            // // 解决中文乱码问题
            // StringEntity entity = new StringEntity(paramStr, "utf-8");
            // entity.setContentEncoding("UTF-8");
            // entity.setContentType("application/json");
            // method.setEntity(entity);
            // }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /** 请求发送成功，并得到响应 **/
            System.out.println(result.getStatusLine().getStatusCode());
            if (result.getStatusLine().getStatusCode() == 200)
            {
                try
                {
                    /** 读取服务器返回过来的json字符串数据 **/
                    resultStr = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse)
                    {
                        return null;
                    }
                    /** 把json字符串转换成json对象 **/
                    // jsonResult = JSONObject.fromObject(str);
                }
                catch (Exception e)
                {
                    // log.error("Query object status by parent failded.{}", e);
                    // logger.error("post请求提交失败:" + url, e);
                }
            }
            else
            {
                // log.error("Parent server faulty, statusCode={}.", result.getStatusLine().getStatusCode());
            }
        }
        catch (IOException e)
        {
            // log.error("Query object status by parent failded.{}", e);
            
        }
        return resultStr;
    }
    
    /**
     * 发送get请求
     * 
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url)
    {
        // get请求返回结果
        JSONObject jsonResult = null;
        try
        {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            
            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                /** 读取服务器返回过来的json字符串数据 **/
                String strResult = EntityUtils.toString(response.getEntity());
                /** 把json字符串转换成json对象 **/
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            }
            else
            {
                // logger.error("get请求提交失败:" + url);
            }
        }
        catch (IOException e)
        {
            // logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }
}
