package DHT;

import simulator.Event;
import simulator.GlobalEventHandler;
import simulator.Message;
import simulator.MessagesObjects.LeaveObject;
import simulator.Network;

public class EventHandler extends GlobalEventHandler {
    public EventHandler(Node node) {
        super(node);
    }

    public void handleEvent(Event event) {
        switch (event.getMessage().getEVENT_TYPE()) {
            case 0:
                this.joinRequestHandler(event);
                break;
            case 1:
                this.joinHandler(event);
                break;
            case 2:
                this.joinAckHandler(event);
                break;
            case 3:
                this.leaveHandler(event);
                break;
        }
    }

    public void joinRequestHandler(Event event) {

        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();
        Integer nodeID = this.node.getID();
        Network network = Network.getInstance();

        boolean insertRight = (senderID > nodeID && nodeID > node.getRight()) || (senderID > nodeID && senderID < node.getRight());
        boolean insertLeft = (senderID < nodeID && nodeID < node.getLeft()) || (senderID < nodeID && senderID > node.getLeft());

        if (insertLeft) {
            Logger.log(Logger.JOIN, nodeID, node.getLeft(), senderID);
            simulator.addEvent(new Event(senderID, senderIP, node.getLeft(), new Message(Message.JOIN), simulator.calculateEventArrivalTime()));
            node.setLeft(senderID);
            network.getNodeByIP(senderIP).setRight(nodeID);
        } else if (insertRight) {
            Logger.log(Logger.JOIN, nodeID, node.getRight(), senderID);
            simulator.addEvent(new Event(senderID, senderIP, node.getRight(), new Message(Message.JOIN), simulator.calculateEventArrivalTime()));
            node.setRight(senderID);
            network.getNodeByIP(senderIP).setLeft(nodeID);
        } else {
            Integer closest = getClosestRouter(event);
            Logger.log(Logger.JOIN_REQUEST, nodeID, closest, senderID);
            simulator.addEvent(new Event(senderID, senderIP, closest, new Message(Message.JOIN_REQUEST), simulator.calculateEventArrivalTime()));
        }
    }

    public void joinHandler(Event event) {
        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();
        Integer nodeID = node.getID();
        Integer nodeIP = node.getIP();
        Network network = Network.getInstance();

        boolean insertRight = (senderID > nodeID && nodeID > node.getRight()) || (senderID > nodeID && senderID < node.getRight());

        Message joinAckMessage = new Message(2);

        Logger.log(Logger.JOIN, nodeID, senderID);
        if (insertRight) {
            simulator.addEvent(new Event(nodeID, nodeIP, node.getLeft(), joinAckMessage, simulator.calculateEventArrivalTime()));
            node.setRight(senderID);
            network.getNodeByIP(senderIP).setLeft(nodeID);
        } else {
            simulator.addEvent(new Event(nodeID, nodeIP, node.getRight(), joinAckMessage, simulator.calculateEventArrivalTime()));
            node.setLeft(senderID);
            network.getNodeByIP(senderIP).setRight(nodeID);
        }
    }

    public void joinAckHandler(Event event) {
        Integer nodeID = node.getID();
        Integer senderID = event.getSenderID();

        Logger.log(Logger.JOIN_ACK, senderID, nodeID);
    }

    public void leaveHandler(Event event) {
        Integer senderID = event.getSenderID();
        Message message = event.getMessage();
        LeaveObject object = (LeaveObject) message.getContent();
        if (senderID == node.getRight()) {
            node.setRight(object.getRight());
        } else {
            node.setLeft(object.getLeft());
        }
    }

    public Integer getClosestRouter(Event event) {
        // Initialisation
        Integer closest = node.getRight();
        Integer senderId = event.getSenderID();

        if ((Math.abs(senderId - node.getLeft())) < (Math.abs(senderId - closest))) {
            closest = node.getLeft();
        }

        for (Integer nodeID : node.getKnownNodes()) {
            if ((Math.abs(senderId - nodeID)) < (Math.abs(senderId - closest))) {
                closest = nodeID;
            }
        }
        return closest;
    }
}
