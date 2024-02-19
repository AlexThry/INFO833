package simulator;

public class Event {
    private Integer senderDHTId;
    private Integer senderIP;
    private Integer routerDHTId;
    private Integer destinationId;
    private Message message;
    private Integer arrivalTime;

    public Event(Integer senderDHTId, Integer senderIP, Integer routerDHTId, Message message) {
        this(senderDHTId, senderIP, routerDHTId, message, null, null);
    }

    public Event(Integer senderDHTId, Integer senderIP, Integer routerDHTId, Message message, Integer destinationId, Integer arrivalTime) {
        this.senderDHTId = senderDHTId;
        this.senderIP = senderIP;
        this.routerDHTId = routerDHTId;
        this.message = message;
        this.destinationId = destinationId;
        this.arrivalTime = arrivalTime;
    }

    public Integer getSenderDHTId() {
        return senderDHTId;
    }

    public Integer getSenderIP() {
        return senderIP;
    }

    public Integer getRouterDHTId() {
        return routerDHTId;
    }

    public Message getMessage() {
        return message;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
