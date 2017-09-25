package edu.nju.fyj.imchat.service.auth;

import edu.nju.fyj.imchat.connection.SessionManager;

/**
 * Created by yajfang on 2017/9/25.
 */
public class TokenManager {
    private TokenGenerator tokenGenerator = TokenGenerator.getInstance();
    private SessionManager sessionManager = SessionManager.getInstance();

    private static volatile TokenManager instance;
    private TokenManager() {}

    public static TokenManager getInstance() {
        if(instance == null) {
            synchronized (TokenManager.class) {
                if(instance == null) {
                    instance = new TokenManager();
                }
            }
        }
        return new TokenManager();
    }

    public String allocateToken(String uid) {
        String token = tokenGenerator.generateToken(uid);
        return token;
    }

    public boolean authToken(String uid, String token) {
        String realToken = sessionManager.getSession(uid).getToken();
        return realToken != null && token.equals(realToken);
    }

    public TokenGenerator getTokenGenerator() {
        return tokenGenerator;
    }

    public void setTokenGenerator(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

}
