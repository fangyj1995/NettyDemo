package edu.nju.fyj.imchat.service.auth;

/**
 * Created by yajfang on 2017/9/25.
 */
public class AuthService {

    private TokenManager tokenManager = TokenManager.getInstance();

    public boolean authLogin(String uid, String password) {//TODO
        return true;
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
