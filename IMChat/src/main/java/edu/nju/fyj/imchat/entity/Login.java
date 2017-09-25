package edu.nju.fyj.imchat.entity;

/**
 * Created by yajfang on 2017/9/25.
 */
public class Login extends Body{
    private String uid;
    private String pwd;

    public Login(String sender, String receiver, String token, String content, String uid, String pwd) {
        super(sender, receiver, token, content);
        this.uid = uid;
        this.pwd = pwd;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
