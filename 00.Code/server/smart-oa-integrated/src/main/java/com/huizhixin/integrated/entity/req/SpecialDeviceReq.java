package com.huizhixin.integrated.entity.req;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 采集接收表
@Data
public class SpecialDeviceReq {
    // 接收到的支付串
    private  String StrRev;
    // 设备信息索引
    private String EquipInfo_IDX;
    // 采集电能
    private String CollectEnergy;
    // 采集电压
    private String CollectVoltage;
    // 采集电流
    private String CollectCurrent;
    // 采集时间
    private String CollectTime;
    // 主键
    private String IDX;

    public static void main(String args[]) throws ParseException {
        String str="2019-11-27T18:26:32.9629228+08:00".replace("T"," ");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt=sdf.parse(str);
        String str1=sdf.format(dt);
        System.out.println("Hello World!");
    }

    //  2019-11-27T18:26:32.9629228+08:00
}
