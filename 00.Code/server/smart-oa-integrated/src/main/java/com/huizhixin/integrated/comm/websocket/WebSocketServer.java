package com.huizhixin.integrated.comm.websocket;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.huizhixin.smart.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * WebSocket服务端示例
 *
 * @author wallimn，http://wallimn.iteye.com
 */
@ServerEndpoint(value = "/ws/asset/{businessID}")
@Component
public class WebSocketServer {

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。  
//    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();  

    /**
     * 通道扩展信息 key: sessionID
     */
    public static Map<String, Session> clientMap = new HashMap<String, Session>();
    /**
     * 保存商户id和唯一标识id的关系   sessionid    bid
     */
    public static Map<String, String> bidMap = new HashMap<String, String>();

    /**
     * 保存商户id和唯一标识id的关系   bid    sessionid
     */
    public static Map<String, String> bidChannelIDMap = new HashMap<String, String>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("businessID") String businessID, Session session) {
//        SessionSet.add(session);

        if (!StringUtils.isEmpty(businessID)) {
            clientMap.put(session.getId(), session);
            bidMap.put(session.getId(), businessID);
            bidChannelIDMap.put(businessID, session.getId());
        }

        int cnt = OnlineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
//        SendMessage(session, "连接成功");  
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
//        SessionSet.remove(session);
        clientMap.remove(session.getId());
        //找到bid
        String bid = bidMap.get(session.getId());
        //删除信息
        bidMap.remove(session.getId());
        bidChannelIDMap.remove(bid);
        int cnt = OnlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}", message);
        SendMessage(session, "收到消息，消息内容：" + message);

    }

    /**
     * 出现错误
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}", error.getMessage(), session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     *
     * @param session
     * @param message
     */
    public static void SendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     *
     * @param message
     * @throws IOException
     */
    public static void BroadCastInfo(String message)  {
        for (Session session : clientMap.values()) {
            if (session.isOpen()) {
                SendMessage(session, message);
            }
        }
    }

    public static void getBusinessMessage(String bussinessID, String oldBusinessID, String orderCode, String message) throws IOException {


    }


    /**
     * 指定商户发送消息
     *
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void SendMessage(String businessId, String message) throws IOException {

        if (businessId != null) {
            //通过bid 找到sessionId
//            String sessionid = bidChannelIDMap.get(businessId);
//            if(!StringUtils.isEmpty(sessionid)){
//              //通过sessionId  找到session发送消息
//                Session session = clientMap.get(sessionid);
//                if(session != null)
//                {
////                    SendMessage(session, message);
//                    session.getBasicRemote().sendText(String.format("%s",message));
//                }else
//                {
//                    log.warn("没有找到你指定ID的会话：{}",businessId);
//                }
//            }else
//            {
//                log.warn("没有找到你指定ID的会话：{}",businessId);
//            }

        } else {
            log.warn("没有找到你指定ID的会话：{}", businessId);
        }
    }


//    /** 
//     * 指定Session发送消息 
//     * @param sessionId 
//     * @param message 
//     * @throws IOException 
//     */  
//    public static void SendMessage(String sessionId,String message) throws IOException {  
//        Session session = null;  
//        for (Session s : SessionSet) {
//            if(s.getId().equals(sessionId)){
//                session = s;  
//                break;
//            }
//        }  
//        if(session!=null){  
//            SendMessage(session, message);
//        }  
//        else{  
//            log.warn("没有找到你指定ID的会话：{}",sessionId);  
//        }
//    }

}  
