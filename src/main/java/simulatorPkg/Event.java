package simulatorPkg;

public class Event {
    private EventType type;
    private Integer senderId;
    private Integer receiverId;
    private Integer routerId;
    private Integer arrivalTime;

    public Event(EventType type, Integer senderId, Integer receiverId, Integer routerId, Integer arrivalTime) {
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.routerId = routerId;
        this.arrivalTime = arrivalTime;
    }

    public EventType getType() {
        return type;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }
}
