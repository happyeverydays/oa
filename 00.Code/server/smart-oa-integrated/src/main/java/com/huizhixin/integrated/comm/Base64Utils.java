package com.huizhixin.integrated.comm;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class Base64Utils {
    /**
     * 图片转化成base64字符串
     *
     * @param imgPath
     * @return
     */
    public static String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = imgPath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return encode;
    }

    /**
     * base64字符串转化成图片
     *
     * @param imgData     图片编码
     * @param imgFilePath 存放到本地路径
     * @param str1
     * @return
     * @throws IOException
     */
    @SuppressWarnings("finally")
    public static boolean GenerateImage(String imgData, String imgFilePath, String str1) { // 对字节数组字符串进行Base64解码并生成图片
        String sourcePath = imgFilePath.substring(0, imgFilePath.lastIndexOf(".")) + "_s" + imgFilePath.substring(imgFilePath.lastIndexOf("."));
        //先保存原始图片
        GenerateImage(imgData, sourcePath);

        try {
            BufferedImage image = ImageIO.read(new File(sourcePath));
            Graphics g = image.getGraphics();
            g.setColor(Color.RED);//画笔颜色
            Graphics2D g2 = (Graphics2D)g;  //g是Graphics对象
            g2.setStroke(new BasicStroke(3.0f));
            String[] strarr = str1.split(", ");

            Arrays.stream(strarr).forEach((item) -> {
                String[] strarr1 = item.split("\\)\\(");


                Double dx0 = getNumber(strarr1[0].split(",")[0]);
                Double dy0 = getNumber(strarr1[0].split(",")[1]);

                Double dx1 = getNumber(strarr1[3].split(",")[0]);
                Double dy1 = getNumber(strarr1[3].split(",")[1]);

                //长宽
                Double dx = dx1 - dx0;
                Double dy = dy1 - dy0;

                // 中心点
                Double x = dx1 - dx0 + dx / 2;
                Double y = dy1 - dy0 + dy / 2;

                g.drawRect(x.intValue(), y.intValue(), dx.intValue(), dy.intValue());//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
            });

            //g.dispose();
            FileOutputStream out = new FileOutputStream(imgFilePath);//输出图片的地址
            ImageIO.write(image, "png", out);
        } catch (Exception e) {
            GenerateImage(imgData, imgFilePath);
        }

        return true;
    }

    public static boolean GenerateImage(String imgData, String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
        if (imgData == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public static void main(String[] args) {
        String str = "[{(2,3)(10,3)(3,20)(10,20)}, {(2,3)(10,3)(3,20)(10,20)}]";
        String[] strarr = str.split(", ");

        Arrays.stream(strarr).forEach((item) -> {
            String[] strarr1 = item.split("\\)\\(");


            Double dx0 = getNumber(strarr1[0].split(",")[0]);
            Double dy0 = getNumber(strarr1[0].split(",")[1]);

            Double dx1 = getNumber(strarr1[3].split(",")[0]);
            Double dy1 = getNumber(strarr1[3].split(",")[1]);

            //长宽
            Double dx = dx1 - dx0;
            Double dy = dy1 - dy0;

            // 中心点
            Double x = dx1 - dx0 + dx / 2;
            Double yy = dy1 - dy0 + dy / 2;
            System.out.println(dx0);
            System.out.println(dy0);

        });


        System.out.println(strarr);


    }

    private static Double getNumber(String str) {
        char[] strChars = str.toCharArray();
        String number = "";
        for (int i = 0; i < strChars.length; i++) {
            try {
                if ((strChars[i] + "").equals(".")) {
                    number += strChars[i];
                    continue;
                }
                int n = Integer.parseInt(strChars[i] + "");
                number += n + "";
            } catch (NumberFormatException e) {
                continue;

            }
        }
        return Double.parseDouble(number);
    }
}
