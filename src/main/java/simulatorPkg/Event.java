package simulatorPkg;

public class Event {
    private Message message;
    private Integer senderId;
    private Integer senderIP;
    private Integer receiverId;
    private Integer routerId;
    private Integer arrivalTime;

    public Event(Message message, Integer senderId, Integer senderIP, Integer receiverId, Integer routerId, Integer arrivalTime) {
        this.message = message;
        this.senderId = senderId;
        this.senderIP = senderIP;
        this.receiverId = receiverId;
        this.routerId = routerId;
        this.arrivalTime = arrivalTime;
    }

    public Message getMessage() {
        return message;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getRouterId() {
        return routerId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Integer getSenderIP() {
        return senderIP;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public void setRouterId(Integer routerId) {
        this.routerId = routerId;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "message=" + message +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", routerId=" + routerId +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
