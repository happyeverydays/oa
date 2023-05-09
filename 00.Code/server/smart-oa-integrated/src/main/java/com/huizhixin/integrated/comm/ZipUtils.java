package com.huizhixin.integrated.comm;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件工具类
 */
public class ZipUtils {

    public static void doCompress(String srcFile, String zipFile)
            throws Exception {
        doCompress(new File(srcFile), new File(zipFile));
    }

    /**
     * 文件压缩
     *
     * @param srcFile  目录或者单个文件
     * @param destFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File destFile)
            throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destFile));
        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            for (File file : files) {
                doCompress(file, out);
            }
        } else {
            doCompress(srcFile, out);
        }
    }

    public static void doCompress(String pathname, ZipOutputStream out)
            throws IOException {
        doCompress(new File(pathname), out);
    }

    public static void doCompress(File file, ZipOutputStream out)
            throws IOException {
        if (file.exists()) {
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file.getName()));
            int len = 0;
            // 读取文件的内容,打包到zip文件
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.closeEntry();
            fis.close();
        }
    }

    /**
     * 解压缩zip包
     *
     * @param zipFilePath        zip文件的全路径
     * @param unzipFilePath      解压后的文件保存的路径
     * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
     */
    public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception {
        File zipFile = new File(zipFilePath);
        //如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径    
        if (includeZipFileName) {
            String fileName = zipFile.getName();
            if (!(fileName == null || fileName.trim().length() == 0)) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            unzipFilePath = unzipFilePath + File.separator + fileName;
        }
        //创建解压缩文件保存的路径    
        File unzipFileDir = new File(unzipFilePath);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
            unzipFileDir.mkdirs();
        }

        //开始解压    
        ZipEntry entry = null;
        String entryFilePath = null, entryDirPath = null;
        File entryFile = null, entryDir = null;
        int index = 0, count = 0, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        Charset gbk = Charset.forName("gbk");

        ZipFile zip = new ZipFile(zipFile, gbk);
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
        //循环对压缩包里的每一个文件进行解压         
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();

            //构建压缩包中一个文件解压后保存的文件全路径    
            entryFilePath = unzipFilePath + File.separator + entry.getName();
            //构建解压后保存的文件夹路径    
            index = entryFilePath.lastIndexOf(File.separator);
            if (index != -1) {
                entryDirPath = entryFilePath.substring(0, index);
            } else {
                entryDirPath = "";
            }
            entryDir = new File(entryDirPath);
            //如果文件夹路径不存在，则创建文件夹    
            if (!entryDir.exists() || !entryDir.isDirectory()) {
                entryDir.mkdirs();
            }

            //创建解压文件    
            entryFile = new File(entryFilePath);
            if (entryFile.exists()) {
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException    
                //SecurityManager securityManager = new SecurityManager();    
                //securityManager.checkDelete(entryFilePath);    
                //删除已存在的目标文件    
                entryFile.delete();

            }
            if (!entry.isDirectory()) {
                //写入文件
                bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                bis = new BufferedInputStream(zip.getInputStream(entry));
                while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                    bos.write(buffer, 0, count);
                }
                bos.flush();
                bos.close();
            } else {
                entryFile.mkdirs();
            }


        }
        zip.close();
    }


    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        doCompress("D:/qrCode", "D:/1.zip");
    }

    public static byte[] fileToBytes(String filePath) {
        byte[] buffer = null;
        File file = new File(filePath);

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            buffer = bos.toByteArray();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException ex) {

            } finally {
                try {
                    if (null != fis) {
                        fis.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return buffer;
    }

    public static void bytesToFile(byte[] buffer, final String filePath) {
        File file = new File(filePath);
        OutputStream output = null;
        BufferedOutputStream bufferedOutput = null;
        try {
            output = new FileOutputStream(file);
            bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bufferedOutput) {
                try {
                    bufferedOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}