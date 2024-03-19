package dht;

import simulator.*;
import simulator.MessagesObjects.LeaveObject;

import java.util.Objects;

public class EventHandler extends GlobalEventHandler {
    public EventHandler(Node node) {
        super(node);
    }

    public void handleEvent(Event event) {
        switch (event.getMessage().getEventType()) {
            case 0 -> this.joinRequestHandler(event);
            case 1 -> this.joinHandler(event);
            case 2 -> this.joinAckHandler(event);
            case 3 -> this.leaveRequestHandler();
            case 4 -> this.leaveHandler(event);
            case 5 -> this.sendHandler(event);
            default -> Logger.log("no event type");
        }
    }

    public void joinRequestHandler(Event event) {

        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();
        Integer nodeID = node.getID();

        if (node.getRight() == null || node.getLeft() == null) {
            Integer router = Network.getRandomDHTNode().getID();
            System.out.println(node + " Node left. Redirecting (joinRequest) to " + router);
            Message joinRequestMessage = new Message(Message.JOIN_REQUEST);
            Event newEvent = new Event(senderID, senderIP, router, joinRequestMessage, Simulator.calculateEventArrivalTime(node.getID(), router));
            Simulator.addEvent(newEvent);
        } else {
            boolean insertRight = (senderID > nodeID && nodeID > node.getRight()) || (senderID > nodeID && senderID < node.getRight());
            boolean insertLeft = (senderID < nodeID && nodeID < node.getLeft()) || (senderID < nodeID && senderID > node.getLeft());

            if (insertLeft) {
                Logger.log(Logger.JOIN, nodeID, node.getLeft(), senderID);
                Simulator.addEvent(new Event(senderID, senderIP, node.getLeft(), new Message(Message.JOIN), simulator.calculateEventArrivalTime(nodeID, node.getLeft())));
                node.setLeft(senderID);
                Network.getNodeByIP(senderIP).setRight(nodeID);
            } else if (insertRight) {
                Logger.log(Logger.JOIN, nodeID, node.getRight(), senderID);
                Simulator.addEvent(new Event(senderID, senderIP, node.getRight(), new Message(Message.JOIN), simulator.calculateEventArrivalTime(nodeID, node.getRight())));
                node.setRight(senderID);
                Network.getNodeByIP(senderIP).setLeft(nodeID);
            } else {
                Integer closest = getClosestRouter(event.getSenderID());
                Logger.log(Logger.JOIN_REQUEST, nodeID, closest, senderID);
                Simulator.addEvent(new Event(senderID, senderIP, closest, new Message(Message.JOIN_REQUEST), simulator.calculateEventArrivalTime(nodeID, closest)));
            }
        }

    }

    private void insertLeft(Integer nodeID, Integer nodeIP, Integer right, Integer senderID, Integer senderIP, Message joinAckMessage) {
        Logger.log(Logger.JOIN, nodeID, senderID);
        Logger.log("JOINING " + senderID + " ON LEFT OF NODE " + nodeID);
        Simulator.addEvent(new Event(nodeID, nodeIP, right, joinAckMessage, simulator.calculateEventArrivalTime(nodeID, right)));
        node.setLeft(senderID);
        Network.getNodeByIP(senderIP).setRight(nodeID);
    }

    private void insertRight(Integer nodeID, Integer nodeIP, Integer left, Integer senderID, Integer senderIP, Message joinAckMessage) {
        Logger.log(Logger.JOIN, nodeID, senderID);
        Logger.log("JOINING " + senderID + " ON RIGHT OF NODE " + nodeID);
        Simulator.addEvent(new Event(nodeID, nodeIP, left, joinAckMessage, simulator.calculateEventArrivalTime(nodeID, left)));
        node.setRight(senderID);
        Network.getNodeByIP(senderIP).setLeft(nodeID);
    }

    public void joinHandler(Event event) {
        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();


        if (node.getLeft() == null || node.getRight() == null) {
            Integer router = Network.getRandomDHTNode().getID();
            System.out.println(node + " Node left. Redirecting (join) to " + router);
            System.out.println(router);
            Message joinRequestMessage = new Message(Message.JOIN_REQUEST);
            Event newEvent = new Event(senderID, senderIP, router, joinRequestMessage, Simulator.calculateEventArrivalTime(node.getID(), router));
            Simulator.addEvent(newEvent);
        } else {
            Integer nodeID = node.getID();
            Integer nodeIP = node.getIP();
            Integer left = node.getLeft();
            Integer right = node.getRight();

            Message joinAckMessage = new Message(Message.JOIN_ACK);

            boolean insertRightEnd = (senderID < nodeID && senderID < left);
            boolean insertLeftStart = (senderID > nodeID && senderID > right);
            boolean insertRight = (senderID > nodeID);

            if (insertRightEnd) {
                insertRight(nodeID, nodeIP, left, senderID, senderIP, joinAckMessage);
            } else if (insertLeftStart) {
                insertLeft(nodeID, nodeIP, right, senderID, senderIP, joinAckMessage);
            } else if (insertRight) {
                insertRight(nodeID, nodeIP, left, senderID, senderIP, joinAckMessage);
            } else {
                insertLeft(nodeID, nodeIP, right, senderID, senderIP, joinAckMessage);
            }
        }
    }

    public void joinAckHandler(Event event) {
        Logger.log("receiving join ack from " + event.getSenderID());
        Integer nodeID = node.getID();
        Integer senderID = event.getSenderID();
        Logger.log(Logger.JOIN_ACK, senderID, nodeID);
    }

    public void leaveRequestHandler() {

        Integer nodeID = node.getID();
        Integer nodeIP = node.getIP();
        Integer left = node.getLeft();
        Integer right = node.getRight();

        Logger.log(Logger.LEAVE_REQUEST, nodeID, left);
        Logger.log(Logger.LEAVE_REQUEST, nodeID, right);

        LeaveObject leaveObject = new LeaveObject(node.getLeft(), node.getRight());

        Message leaveMessage = new Message(Message.LEAVE, leaveObject);

        Simulator.addEvent(new Event(nodeID, nodeIP, node.getLeft(), leaveMessage, simulator.calculateEventArrivalTime(nodeID, node.getLeft())));
        Simulator.addEvent(new Event(nodeID, nodeIP, node.getRight(), leaveMessage, simulator.calculateEventArrivalTime(nodeID, node.getRight())));

        for (Integer knownNode: node.getKnownNodes()) {
            Logger.log(Logger.LEAVE_REQUEST, nodeID, knownNode);
            Simulator.addEvent(new Event(nodeID, nodeIP, knownNode, leaveMessage, simulator.calculateEventArrivalTime(nodeID, knownNode)));
        }

        node.setLeft(null);
        node.setRight(null);
        node.getKnownNodes().clear();
    }

    public void leaveHandler(Event event) {
        Integer senderID = event.getSenderID();
        Integer nodeID = node.getID();

        Message message = event.getMessage();

        LeaveObject object = (LeaveObject) message.getContent();

        Logger.log(Logger.LEAVE, nodeID, senderID);
        if (Objects.equals(senderID, node.getRight())) {
            node.setRight(object.getRight());
        } else if (Objects.equals(senderID, node.getLeft())) {
            node.setLeft(object.getLeft());
        } else {
            node.getKnownNodes().remove(senderID);
        }
    }

    public void sendHandler(Event event) {
        Logger.log("Sending message");

    }

    public Integer getClosestRouter(Integer senderId) {
        // Initialisation
        Integer closest = node.getRight();

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
