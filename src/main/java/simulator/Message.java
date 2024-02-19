package simulator;

import java.lang.reflect.Type;

public class Message {

    public static Integer JOIN_REQUEST = 0;
    public static Integer JOIN = 1;
    public static Integer JOIN_ACK = 2;
    public static Integer LEAVE = 3;
    public static Integer LEAVE_ACK = 4;

    private Integer EVENT_TYPE;
    private String content;

    public Message(Integer EVENT_TYPE, String content) {
        this.EVENT_TYPE = EVENT_TYPE;
        this.content = content;
    }

    public Message(Integer EVENT_TYPE) {
        this(EVENT_TYPE, null);
    }

    public Integer getEVENT_TYPE() {
        return EVENT_TYPE;
    }

    public String getContent() {
        return content;
    }
}
