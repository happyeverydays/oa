package com.huizhixin.integrated.comm;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;


/**
 * 自建文件服务器工具类
 *
 * @author Administrator
 */
@Component
public class LocalFileServerUtil {

    private static String basedir = "d:/soft/tomcat/webapps/upload/";

    private static String webbasedir;
    @Value("${smart.path.geturl}")
    private String _webbasedir;


    @Value("${smart.path.upload}")
    private String _baseDir;

    private static String model3dbasedir = "d:/soft/tomcat/webapps/upload/";

    @Value("${smart.path.model3dupload}")
    private  String _model3dbasedir;

    @PostConstruct
    public void setBaseDirValue() {
        basedir = this._baseDir;
        webbasedir = this._webbasedir;
        model3dbasedir = this._model3dbasedir;
    }

    //  file_server_ip
    private static final String serverAddress ="";
    /**
     * 文件服务器文件存储初始目录绝对路径，配置 file_server_initPath
     */
    private static final String dir = basedir + "user/";

    private static final String tempdir = basedir + "tempdir/";

    private int aaa;

    /**
     * 創建目錄
     *
     * @param dirName 目錄名
     */
    public static void mkDir(String dirName) {
        File file = new File(dir + dirName);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获得存储文件全路径
     *
     * @param scenePath 场景路径
     * @return
     */
    public static String getFileSavePath(String scenePath) {
        String fullPath = dir + scenePath;
        File path = new File(fullPath);
        if (!path.exists()) {
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
    public static String copyFile(File file, String filePath, String fileName) {
        File path = new File(basedir + filePath);
        if (!path.exists()) {
            path.mkdirs();
        }

        File temp = new File(basedir + filePath + fileName);
        if (temp.exists()) {
            temp.delete();
        }

        String fileAccessFile = null;
        try {
            temp.createNewFile();
            FileUtils.copyFile(file, temp);
            fileAccessFile = "/" + filePath + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileAccessFile;
    }

    public static String getFileAbsolutePath(String filePath) {
        return dir + filePath;
    }

    public static void deleteFile(String filePath) {
        File file = new File(getFileAbsolutePath(filePath));
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 获得文件访问地址
     *
     * @param filePath
     * @return
     */
    public static String getAccessFile(String filePath) {
        return serverAddress + filePath;
    }

    public static String getDir() {
        return dir;
    }

    public static String getTempdir() {
        return tempdir;
    }

    public static String getBasedir() {
        return basedir;
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static String getWebUrl(String file) {
        String url = "";
        if (file != null && file.length() > 0) {
            url = webbasedir + file;
        }

        return url;
    }

    public static String getFaceWebUrl(String file) {
        String url = "";
        if (file != null && file.length() > 0) {
            url = webbasedir + file;
        }

        return url;
    }

    public static String getWebbasedir() {
        return webbasedir;
    }

    public static String getModel3dbasedir() {
        return model3dbasedir;
    }
}
