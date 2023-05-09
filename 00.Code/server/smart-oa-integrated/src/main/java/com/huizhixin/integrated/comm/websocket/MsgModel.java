package com.huizhixin.integrated.comm.websocket;

import lombok.Data;

@Data
public class MsgModel extends BaseMsgModel {
    private Integer type;
    private String direction;
}
