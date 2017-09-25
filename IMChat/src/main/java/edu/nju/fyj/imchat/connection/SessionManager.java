package edu.nju.fyj.imchat.connection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yajfang on 2017/9/25.
 */
public class SessionManager {
    private final Map<String, ClientSession> sessionPool;

    private volatile static SessionManager managerInstance;

    private SessionManager() {
        sessionPool = new ConcurrentHashMap<String, ClientSession>();
    }

    public static SessionManager getInstance() {
        if(managerInstance == null) {
            synchronized (SessionManager.class) {
                if(managerInstance == null)
                    managerInstance = new SessionManager();
            }
        }
        return managerInstance;
    }

    public boolean hasSession(String uid) {
        return sessionPool.containsKey(uid);
    }

    public ClientSession getSession(String uid) {
        return sessionPool.get(uid);
    }

    public void addSession(ClientSession session) {
        ClientSession previous = sessionPool.get(session.getUid());
        if(previous != null) {//重复登录
            previous.close();
        }
        sessionPool.put(session.getUid(), session);
    }

    public void expireClientSession(String uid) {
        ClientSession session = sessionPool.remove(uid);
        session.close();
    }


}
