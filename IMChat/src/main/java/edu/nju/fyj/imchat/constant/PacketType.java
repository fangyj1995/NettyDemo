package edu.nju.fyj.imchat.constant;

/**
 * Created by yajfang on 2017/9/25.
 */
public class PacketType {

    public final static byte REQUEST = 0;

    /*MSG*/

    public final static byte PERSON_MSG = 01;

    public final static byte GROUP_MSG = 02;

    public final static byte MSG_RESPONSE = 03;

    /*AUTH*/

    public final static byte LOGIN_REQUEST = 10;

    public final static byte LOGIN_OK = 11;

    public final static byte LOGIN_FAIL = 12;

    public final static byte TOKEN_ERROR = 12;

    /*HEARTBEAT*/

    public final static byte HEARTBEAT_REQUEST = 20;

    public final static byte HEARTBEAT_RESPONSE = 21;

    /*SERVER*/

    public final static byte SERVER_BUSY = 30;

    public final static byte SERVER_ERROR = 31;



}
