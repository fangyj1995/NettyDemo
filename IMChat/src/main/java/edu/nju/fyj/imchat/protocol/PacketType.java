package edu.nju.fyj.imchat.protocol;

/**
 * Created by yajfang on 2017/9/25.
 */
public class PacketType {

    public final static int REQUEST = 0;

    public final static int SINGLE_MSG = 001;

    public final static int GROUP_MSG = 002;

    public final static int RESPONSE = 010;

    public final static int HANDSHAKE_REQUEST = 100;

    public final static int HANDSHAKE_RESPONSE = 101;

    public final static int HEARTBEAT_REQUEST = 200;

    public final static int HEARTBEAT_RESPONSE = 201;



}
