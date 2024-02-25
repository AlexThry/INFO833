package simulator;

public class Event {
    // ID et IP de l'envoyeur initial
    private Integer senderID;
    private Integer senderIP;
    // ID du dernier noeud ayant relayé l'evenement
    private Integer routerID;
    // ID du destinataire
    private Integer destinationId;
    private Message message;
    private Integer arrivalTime;

    public Event(Integer senderID, Integer senderIP, Integer routerID, Message message) {
        this(senderID, senderIP, routerID, message, null, null);
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
}
