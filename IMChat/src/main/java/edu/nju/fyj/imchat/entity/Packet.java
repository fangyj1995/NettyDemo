package edu.nju.fyj.imchat.entity;

import edu.nju.fyj.imchat.protocol.Header;

/**
 * Created by fangyj on 2017/9/24.
 */
public class Packet {
    private Header header;
    private Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
