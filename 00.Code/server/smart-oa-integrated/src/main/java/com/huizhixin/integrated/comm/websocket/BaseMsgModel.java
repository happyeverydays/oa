package com.huizhixin.integrated.comm.websocket;

import lombok.Data;

@Data
public class BaseMsgModel {

    /**
     * 类型
     * */
    private String key;

    private Object result;


}
