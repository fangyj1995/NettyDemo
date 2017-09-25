package edu.nju.fyj.imchat.service.message;

import edu.nju.fyj.imchat.connection.ClientSession;

/**
 * Created by yajfang on 2017/9/25.
 */
public interface MessageService {
    public void sendSingleMessage(ClientSession from, ClientSession to);
    public void sendGroupMessage(ClientSession from, ClientSession to);
}
