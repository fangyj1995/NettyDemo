package edu.nju.fyj.imchat.entity.body;

/**
 * Created by fangyj on 2017/9/25.
 */
public class TokenResponse extends Body{
    private String token;

    @Override
    public String toString() {
        return "TokenResponse{" +
                "token='" + token + '\'' +
                "} " + super.toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
