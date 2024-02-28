package simulator;

public class Message {

    public static final Integer JOIN_REQUEST = 0;
    public static final Integer JOIN = 1;
    public static final Integer JOIN_ACK = 2;
    public static final Integer LEAVE_REQUEST = 3;
    public static final Integer LEAVE = 4;
    public static final Integer LEAVE_ACK = 5;


    private final Integer eventType;
    private final MessageObject content;

    public Message(Integer eventType) {
        this(eventType, null);
    }

    public Message(Integer eventType, MessageObject content) {
        this.eventType = eventType;
        this.content = content;
    }

    public Integer getEventType() {
        return eventType;
    }

    public MessageObject getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "EVENT_TYPE=" + eventType +
                ", content=" + content  +
                '}';
    }
}
