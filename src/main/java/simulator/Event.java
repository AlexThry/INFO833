package simulator;

public class Event {
    // ID et IP de l'envoyeur initial
    private Integer senderID;
    private Integer senderIP;
    // ID du prochain noeud relayeur
    private Integer routerID;
    // ID du destinataire (peut être nul si on ne connait pas le destinataire)
    private Integer destinationId;
    private Message message;
    private Integer arrivalTime;

//    public Event(Integer senderID, Integer senderIP, Integer routerID, Message message) {
//        this(senderID, senderIP, routerID, message, null, null);
//    }

    public Event(Integer senderID, Integer senderIP, Message message, Integer arrivalTime) {
        this(senderID, senderIP,null, message, null, arrivalTime);
    }

    public Event(Integer senderID, Integer senderIP, Integer routerID, Message message, Integer arrivalTime) {
        this(senderID, senderIP, routerID, message, null, arrivalTime);
    }

    public Event(Integer senderID, Integer senderIP, Message message, Integer destinationId, Integer arrivalTime) {
        this(senderID, senderIP,null, message, destinationId, arrivalTime);
    }

    public Event(Integer senderID, Integer senderIP, Integer routerID, Message message, Integer destinationId, Integer arrivalTime) {
        this.senderID = senderID;
        this.senderIP = senderIP;
        this.routerID = routerID;
        this.message = message;
        this.destinationId = destinationId;
        this.arrivalTime = arrivalTime;
    }

    public Integer getSenderID() {
        return senderID;
    }

    public Integer getSenderIP() {
        return senderIP;
    }

    public Integer getRouterID() {
        return routerID;
    }

    public Message getMessage() {
        return message;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "senderID=" + senderID +
                ", routerID=" + routerID +
                ", destinationId=" + destinationId +
                ", message=" + message +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
