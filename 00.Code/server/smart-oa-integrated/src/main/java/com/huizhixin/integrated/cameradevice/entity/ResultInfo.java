package com.huizhixin.integrated.cameradevice.entity;

import lombok.Data;

@Data
public class ResultInfo<T>
{
    // 表示接口是否调通
    // 0 成功; 1,参数设置错误；2,密码错误；
    private Integer result;
    
    // 表示接口返回的结果的描述信息，通常是错误类型码的原因信息
    private String message;
    
    // 接口返回的业务数据，类型可为空、数值、字符串或集合等
    private T data;
}
