package edu.nju.fyj.imchat.entity.body;

/**
 * Created by yajfang on 2017/9/25.
 */
public class Login extends Body{
    private String pwd;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Login{" +
                "pwd='" + pwd + '\'' +
                ", uid='" + uid + '\'' +
                "} " + super.toString();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
