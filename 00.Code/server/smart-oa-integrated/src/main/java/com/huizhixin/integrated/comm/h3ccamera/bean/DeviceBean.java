package com.huizhixin.integrated.comm.h3ccamera.bean;

import lombok.Data;


@Data
public class DeviceBean {
    private String org_code;
    private String device_code;
    private String name;
    private Integer category;
    private String ip;
    private Integer online_status;
    private String longitude;
    private String latitude;
}
