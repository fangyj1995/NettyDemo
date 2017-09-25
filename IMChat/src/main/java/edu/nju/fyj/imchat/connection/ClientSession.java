package edu.nju.fyj.imchat.connection;


import io.netty.channel.Channel;

import java.util.Date;

/**
 * Created by yajfang on 2017/9/25.
 */
public class ClientSession {
    private Channel channel;
    private String token;
    private String uid;
    private long createTime = new Date().getTime();

    public ClientSession(Channel channel, String token, String uid) {
        this.channel = channel;
        this.token = token;
        this.uid = uid;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void close() {
        channel.close();
    }
}
