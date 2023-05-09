package com.huizhixin.integrated.comm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;

public class ImageBase64Util
{
    public static String getImageBase64(String imageUrl)
    {
        if (StringUtils.isEmpty(imageUrl))
        {
            return null;
        }
        
        String base64Str = "";
        if (isNetAddress(imageUrl))
        {
            base64Str = getNetImageStr(imageUrl);
        }
        else
        {
            base64Str = getImageStr(imageUrl);
        }
        
        return base64Str;
    }
    
    public static boolean isNetAddress(String url)
    {
        String head = url.substring(0, 4);
        if ("http".equals(head))
        {
            
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    private static String getImageStr(String imgFile)
    {
        if (StringUtils.isEmpty(imgFile))
        {
            return "";
        }
        
        InputStream in = null;
        byte[] data = null;
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        return new String(Base64.encodeBase64(data));
    }
    
    private static String getNetImageStr(String imgFileUrl)
    {
        if (StringUtils.isEmpty(imgFileUrl))
        {
            return "";
        }
        
        ByteArrayOutputStream outPut = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        try
        {
            // String urlEncodee= URLEncoder.encode(imgFileUrl, "UTF-8");
            
            String srtname = imgFileUrl.substring(imgFileUrl.lastIndexOf("/") + 1);
            
            imgFileUrl =
                imgFileUrl.substring(0, imgFileUrl.lastIndexOf("/")) + "/" + URLEncoder.encode(srtname, "utf-8");
            
            // 创建URL
            URL url = new URL(imgFileUrl);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(60 * 1000);
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                return "";// 连接失败/链接失效/图片不存在
            }
            InputStream inStream = conn.getInputStream();
            int len = -1;
            while ((len = inStream.read(data)) != -1)
            {
                outPut.write(data, 0, len);
            }
            inStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outPut.toByteArray());
    }
    
}
