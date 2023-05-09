package com.huizhixin.smart.common.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.huizhixin.smart.common.config.ConfigManager;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 自建文件服务器工具类
 * 
 * @author Administrator
 * 
 */
public class LocalFileServerUtil
{
    /**
     * 文件服务器文件存储初始目录绝对路径，配置
     */
    private static final String dir = ConfigManager.getConfigValue("file_server_initPath");
    
    private static final String serverAddress = ConfigManager.getConfigValue("file_server_ip");
    
    /**
     * 創建目錄
     * 
     * @param dirName 目錄名
     */
    public static void mkDir(String dirName)
    {
        File file = new File(dir + dirName);
        if (!file.exists())
        {
            file.mkdirs();
        }
    }
    
    /**
     * 获得存储文件全路径
     * 
     * @param scenePath 场景路径
     * @return
     */
    public static String getFileSavePath(String scenePath)
    {
        String fullPath = dir + scenePath;
        File path = new File(fullPath);
        if (!path.exists())
        {
            path.mkdirs();
        }
        return fullPath;
    }
    
    /**
     * 將文件存儲到文件服務器側
     * 
     * @param file
     * @param filePath
     * @param fileName
     * @return 返回的路径为相对路径，不包括预置目录名
     */
    public static String copyFile(File file, String filePath, String fileName)
    {
        File path = new File(dir + filePath);
        if (!path.exists())
        {
            path.mkdirs();
        }
        
        File temp = new File(dir + filePath + fileName);
        if (temp.exists())
        {
            temp.delete();
        }
        
        String fileAccessFile = null;
        try
        {
            temp.createNewFile();
            FileUtils.copyFile(file, temp);
            fileAccessFile = "/" + filePath + fileName;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return fileAccessFile;
    }
    
    public static String getFileAbsolutePath(String filePath)
    {
        return dir + filePath;
    }
    
    public static void deleteFile(String filePath)
    {
        File file = new File(getFileAbsolutePath(filePath));
        if (file.exists())
        {
            file.delete();
        }
    }
    
    /**
     * 获得文件访问地址
     * 
     * @param filePath
     * @return
     */
    public static String getAccessFile(String filePath)
    {
        System.out.println(serverAddress);
        return serverAddress + filePath;
    }
    
    
    /**
     * 生成缩略图
     * 
     * @param filePath
     */
    public static void generateThumbnails(String filePath)
    {
        String fromFile = LocalFileServerUtil.getFileAbsolutePath(filePath);
        
        int index = filePath.lastIndexOf("/");
        String path = filePath.substring(0, index + 1);
        String fileName = filePath.substring(index + 1, filePath.length());
        String newFilename = "thumb_" + fileName;
        
        String toFile = dir + path + newFilename;
        
        try
        {
            Thumbnails.of(fromFile).scale(0.5f).toFile(toFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static String getThumbnails(String filePath, boolean create)
    {
        int index = filePath.lastIndexOf("/");
        String path = filePath.substring(0, index + 1);
        String fileName = filePath.substring(index + 1, filePath.length());
        String newFilename = "thumb_" + fileName;
        
        String thumbPath = path + newFilename;
        
        if (create)
        {
            File file = new File(dir + thumbPath);
            if (!file.exists())
            {
                generateThumbnails(filePath);
            }
        }
        return serverAddress + thumbPath;
    }
    
}
