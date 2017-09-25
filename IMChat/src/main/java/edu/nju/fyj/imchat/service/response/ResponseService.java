package edu.nju.fyj.imchat.service.response;


import edu.nju.fyj.imchat.connection.ClientSession;
import io.netty.channel.Channel;

/**
 * Created by yajfang on 2017/9/25.
 */
public interface ResponseService {
    public void sendLoginSuccess(ClientSession session);
    public void sendLoginError(Channel channel);
    public void sendAuthError(Channel channel);
    public void sendHeartbeat(Channel channel);
}
