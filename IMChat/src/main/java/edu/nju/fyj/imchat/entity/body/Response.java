package edu.nju.fyj.imchat.entity.body;

/**
 * Created by fangyj on 2017/9/25.
 */
public class Response extends Body{
    private String content;

    @Override
    public String toString() {
        return "Response{" +
                "content='" + content + '\'' +
                "} " + super.toString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
