package edu.nju.fyj.imchat.service.auth;

import java.util.UUID;

/**
 * Created by yajfang on 2017/9/25.
 */
public class TokenGenerator {

    public static String generateToken(String uid) {//todo
        return UUID.randomUUID().toString();
    }

}
