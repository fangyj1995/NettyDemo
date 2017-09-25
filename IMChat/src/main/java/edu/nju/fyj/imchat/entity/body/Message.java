package edu.nju.fyj.imchat.entity.body;

/**
 * Created by yajfang on 2017/9/25.
 */
public class Message extends Body{

    private String sender;
    private String receiver;
    private String token;
    private String content;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        return "Message{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", token='" + token + '\'' +
                ", content='" + content + '\'' +
                "} " + super.toString();
    }
}
