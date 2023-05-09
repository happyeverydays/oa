package com.huizhixin.integrated.entity.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShipMonitorCurrentInfoBean {
    /// <summary>
    /// 空压机电能
    /// </summary>
    private String Power_1;
    /// <summary>
    /// 空压机A相电压
    /// </summary>
    private String Voltage_A_1;
    /// <summary>
    /// 空压机B相电压
    /// </summary>
    private String Voltage_B_1;
    /// <summary>
    /// 空压机C相电压
    /// </summary>
    private String Voltage_C_1;
    /// <summary>
    /// 空压机A相电流
    /// </summary>
    private String Current_A_1;
    /// <summary>
    /// 空压机B相电流
    /// </summary>
    private String Current_B_1;
    /// <summary>
    /// 空压机C相电流
    /// </summary>
    private String Current_C_1;

    /// <summary>
    /// 高低温箱电能
    /// </summary>
    private String Power_2;
    /// <summary>
    /// 高低温箱A相电压
    /// </summary>
    private String Voltage_A_2;
    /// <summary>
    /// 高低温箱B相电压
    /// </summary>
    private String Voltage_B_2;
    /// <summary>
    /// 高低温箱C相电压
    /// </summary>
    private String Voltage_C_2;
    /// <summary>
    /// 高低温箱A相电流
    /// </summary>
    private String Current_A_2;
    /// <summary>
    /// 高低温箱B相电流
    /// </summary>
    private String Current_B_2;
    /// <summary>
    /// 高低温箱C相电流
    /// </summary>
    private String Current_C_2;

    /// <summary>
    /// 焊机电能
    /// </summary>
    private String Power_3;
    /// <summary>
    /// 焊机A相电压
    /// </summary>
    private String Voltage_A_3;
    /// <summary>
    /// 焊机B相电压
    /// </summary>
    private String Voltage_B_3;
    /// <summary>
    /// 焊机C相电压
    /// </summary>
    private String Voltage_C_3;

    /// <summary>
    /// 焊机A相电流
    /// </summary>
    private String Current_A_3;
    /// <summary>
    /// 焊机B相电流
    /// </summary>
    private String Current_B_3;
    /// <summary>
    /// 焊机C相电流
    /// </summary>
    private String Current_C_3;


    /// <summary>
    /// 空调电能
    /// </summary>
    private String Power_4;
    /// <summary>
    /// 空调电压
    /// </summary>
    private String Voltage_4;
    /// <summary>
    /// 空调电流
    /// </summary>
    private String Current_4;


    /// <summary>
    /// 电机电能
    /// </summary>
    private String Power_5;
    /// <summary>
    /// 电机电压
    /// </summary>
    private String Voltage_5;
    /// <summary>
    /// 电机电流
    /// </summary>
    private String Current_5;

    private String CollectTime;
}
