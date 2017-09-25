package edu.nju.fyj.imchat.service.auth;

import java.util.HashMap;

/**
 * Created by yajfang on 2017/9/25.
 */
public class AuthService {

    private TokenManager tokenManager = TokenManager.getInstance();

    private static HashMap<String, String> whiteList = new HashMap<String, String>();
    static {
        whiteList.put("fyj","12345");
        whiteList.put("lyj","12345");
        whiteList.put("lcy","12345");
        whiteList.put("csh","12345");
    }

    public boolean authLogin(String uid, String password) {
        return password.equals(whiteList.get(uid));
    }

    public boolean authToken(String uid, String token) {
        return tokenManager.authToken(uid, token);
    }

    public String allocateToken(String uid) {
        return tokenManager.allocateToken(uid);
    }

    public TokenManager getTokenManager() {
        return tokenManager;
    }

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }
}
