package com.huizhixin.integrated.comm.websocket.impl;

import com.huizhixin.integrated.comm.websocket.BaseMsgModel;

public class MsgModel extends BaseMsgModel
{

    /**
     * 方向
     * */
    private String direction;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
