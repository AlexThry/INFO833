package simulator;

public class Event {
    private Integer senderDHTId;
    private Integer senderIP;
    private Integer receiverDHTId;
    private Integer arrivalTime;

    public Event(Integer senderDHTId, Integer senderIP, Integer receiverDHTId) {
        this(senderDHTId, senderIP, receiverDHTId, null);
    }

    public Event(Integer senderDHTId, Integer senderIP, Integer receiverDHTId, Integer arrivalTime) {
        this.senderDHTId = senderDHTId;
        this.senderIP = senderIP;
        this.receiverDHTId = receiverDHTId;
        this.arrivalTime = arrivalTime;
    }

    public Integer getSenderDHTId() {
        return senderDHTId;
    }

    public Integer getSenderIP() {
        return senderIP;
    }

    public Integer getReceiverDHTId() {
        return receiverDHTId;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
