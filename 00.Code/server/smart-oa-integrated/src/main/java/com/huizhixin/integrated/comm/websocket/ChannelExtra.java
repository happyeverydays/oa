package com.huizhixin.integrated.comm.websocket;

import io.netty.channel.ChannelHandlerContext;

public class ChannelExtra
{
    private String id;
    
    private String type;
    
    private ChannelHandlerContext channel;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public ChannelHandlerContext getChannel()
    {
        return channel;
    }

    public void setChannel(ChannelHandlerContext channel)
    {
        this.channel = channel;
    }
    
}
