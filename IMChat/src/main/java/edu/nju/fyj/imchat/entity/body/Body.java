package edu.nju.fyj.imchat.entity.body;

import java.util.Date;

/**
 * Created by yajfang on 2017/9/25.
 */
public abstract class Body {
    private long createTime;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Body{" +
                "createTime=" + createTime +
                '}';
    }
}
