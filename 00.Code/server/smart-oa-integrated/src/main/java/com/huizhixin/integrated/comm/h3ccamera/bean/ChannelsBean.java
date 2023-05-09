package com.huizhixin.integrated.comm.h3ccamera.bean;

import lombok.Data;

@Data
public class ChannelsBean {
    private String org_code;
    private String camera_id;
    private String name;
    private String device_code;
    private Integer unit_type;
    private Integer camera_type;
    private Integer online_status;
    private String longitude;
    private String latitude;
}