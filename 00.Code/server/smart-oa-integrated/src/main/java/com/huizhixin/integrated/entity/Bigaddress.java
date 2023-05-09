package com.huizhixin.integrated.entity;

import lombok.Data;

@Data
public class Bigaddress {
    private String id;
    private String url;
    private String channelid;
    private Double minutes;
    private String videoname;
    private String distchannel;
    private String ipaddress;
    private String username;
    private String password;
    private String port;
    //  摄像机类型：1、大华 2、海康威视
    private String cameratype;


}
