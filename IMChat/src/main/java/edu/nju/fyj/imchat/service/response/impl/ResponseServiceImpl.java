package edu.nju.fyj.imchat.service.response.impl;

import edu.nju.fyj.imchat.connection.ClientSession;
import edu.nju.fyj.imchat.service.response.ResponseService;
import io.netty.channel.Channel;

/**
 * Created by yajfang on 2017/9/25.
 */
public class ResponseServiceImpl implements ResponseService {
    public void sendLoginSuccess(ClientSession session) {

    }

    public void sendLoginError(Channel channel) {

    }

    public void sendAuthError(Channel channel) {

    }

    public void sendHeartbeat(Channel channel) {

    }
}
