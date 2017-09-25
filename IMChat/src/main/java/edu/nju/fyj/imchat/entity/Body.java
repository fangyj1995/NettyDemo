package edu.nju.fyj.imchat.entity;

import java.util.Date;

/**
 * Created by yajfang on 2017/9/25.
 */
public class Body {
    private long createTime = new Date().getTime();
    private String sender;
    private String receiver;
    private String token;
    private String content;

    public Body(String sender, String receiver, String token, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.token = token;
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Body{" +
                "createTime=" + createTime +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
