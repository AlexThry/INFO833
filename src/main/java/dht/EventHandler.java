package dht;

import simulator.*;
import simulator.MessagesObjects.LeaveObject;
import simulator.MessagesObjects.SendObject;

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
            case 6 -> this.forwardHandler(event);
            case 7 -> this.deliverHandler(event);
            default -> Logger.log("no event type");
        }
    }

    public void joinRequestHandler(Event event) {

        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();
        Integer nodeID = node.getID();

        if (node.getRight() == null || node.getLeft() == null) {
            System.out.println("Node left. Redirecting (joinRequest)");
            Integer router = Network.getRandomDHTNode(senderID).getID();
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
                Integer closest = getClosestRouterJoin(event.getSenderID());
                Logger.log(Logger.JOIN_REQUEST, nodeID, closest, senderID);
                Simulator.addEvent(new Event(senderID, senderIP, closest, new Message(Message.JOIN_REQUEST), simulator.calculateEventArrivalTime(nodeID, closest)));
            }
        }

    }

    private void insertLeft(Integer nodeID, Integer nodeIP, Integer right, Integer senderID, Integer senderIP, Message joinAckMessage) {
        Logger.log(Logger.JOIN, nodeID, senderID);
        Logger.log("JOINING " + senderID + " ON LEFT OF NODE " + nodeID);
        Simulator.addEvent(new Event(nodeID, nodeIP, right, joinAckMessage,senderID, simulator.calculateEventArrivalTime(nodeID, right)));
        node.setLeft(senderID);
        Network.getNodeByIP(senderIP).setRight(nodeID);
    }

    private void insertRight(Integer nodeID, Integer nodeIP, Integer left, Integer senderID, Integer senderIP, Message joinAckMessage) {
        Logger.log(Logger.JOIN, nodeID, senderID);
        Logger.log("JOINING " + senderID + " ON RIGHT OF NODE " + nodeID);
        Simulator.addEvent(new Event(nodeID, nodeIP, left, joinAckMessage, senderID, simulator.calculateEventArrivalTime(nodeID, left)));
        node.setRight(senderID);
        Network.getNodeByIP(senderIP).setLeft(nodeID);
    }

    public void joinHandler(Event event) {
        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();


        if (node.getLeft() == null || node.getRight() == null) {
            System.out.println("Node left. Redirecting (join)");
            Integer router = Network.getRandomDHTNode(senderID).getID();
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
        Integer joiningNode = event.getDestinationId();
        Integer senderID = event.getSenderID();
        Logger.log(Logger.JOIN_ACK, senderID, joiningNode);
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
        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();

        if (node.getRight() == null || node.getLeft() == null) {
            Logger.log("\n----------\n"+"The sender " + senderID + " is not in the DHT."+"\n----------\n");
            return;
        }

        Integer destinationIdID = event.getDestinationId();
        Integer nodeID = node.getID();

        Message message = event.getMessage();
        SendObject object = (SendObject) message.getContent();
        Integer closest = getClosestRouterSend(destinationIdID);

        Logger.log(Logger.SEND, closest, destinationIdID, senderID);

        if (closest.equals(destinationIdID)) {
            Message deliverMessage = new Message(Message.DELIVER, object);
            Event deliverEvent = new Event(senderID, senderIP, destinationIdID, deliverMessage, destinationIdID, Simulator.calculateEventArrivalTime(nodeID, destinationIdID));
            Simulator.addEvent(deliverEvent);
        } else {
            // Transférons le message à un nœud plus proche
            Message forwardMessage = new Message(Message.FORWARD, object);
            Event newEvent = new Event(senderID, senderIP, closest, forwardMessage, destinationIdID, simulator.calculateEventArrivalTime(nodeID, closest));
            Simulator.addEvent(newEvent);
        }

    }

    public boolean handleSendLeaveNode(Event event) {
        Integer senderID = event.getSenderID();
        Integer nodeID = node.getID();
        Boolean destinationNotGone = true;
        if (senderID > nodeID) { // tourne vers la droite dans le cercle
            destinationNotGone = node.getLeft() != null || node.getLeft() < nodeID;
        } else if (senderID < nodeID) { // tourne vers la gauche
            destinationNotGone = node.getRight() != null || node.getRight() > nodeID;
        } else {
            destinationNotGone = false;
        }
        return destinationNotGone;
    }


    public void forwardHandler(Event event) {
        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();
        Integer routerID = event.getRouterID();
        Integer destinationIdID = event.getDestinationId();
        Integer nodeID = node.getID();

        if (node.getRight() == null || node.getLeft() == null) {
            Logger.log("\n----------\n"+"The sender " + senderID + " is not in the DHT."+"\n----------\n");
            return;
        }

        Boolean noReceiver =!handleSendLeaveNode(event);
        if (noReceiver){
            Logger.log("\n----------\n"+"The receiver " + destinationIdID + " has left, send not possible."+"\n----------\n");
            return;
        }

        Message message = event.getMessage();
        SendObject object = (SendObject) message.getContent();
        Integer closest = getClosestRouterSend(destinationIdID);

        Logger.log(Logger.FORWARD, closest, destinationIdID, routerID);

        if (closest.equals(destinationIdID)) {
            Message deliverMessage = new Message(Message.DELIVER, object);
            Event deliverEvent = new Event(senderID, senderIP, destinationIdID, deliverMessage, destinationIdID, Simulator.calculateEventArrivalTime(nodeID, destinationIdID));
            Simulator.addEvent(deliverEvent);
        } else {
            Message forwardMessage = new Message(Message.FORWARD, object);
            Event newEvent = new Event(senderID, senderIP, closest, forwardMessage,destinationIdID, simulator.calculateEventArrivalTime(nodeID, closest));
            Simulator.addEvent(newEvent);
        }
    }

    public void deliverHandler(Event event) {
        Message message = event.getMessage();
        SendObject object = (SendObject) message.getContent();
        Logger.log(Logger.DELIVER, node.getID(), event.getDestinationId(), event.getSenderID());
//        Logger.log("Sender :" + event.getSenderID());
//        Logger.log("Receiver :" + node.getID());
//        Logger.log("Content :" + object.getMessage());
    }

    public Integer getClosestRouterJoin(Integer senderId) {
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

    public Integer getClosestRouterSend(Integer receiverId) {
        Integer closest = node.getRight();
        Integer closestGap = Math.abs(receiverId - closest);
        if (Math.abs(receiverId - node.getLeft()) < closestGap) {
            closest = node.getLeft();
            closestGap = Math.abs(receiverId - closest);
        }

        for (int nodeID : node.getKnownNodes()) {
            if (Math.abs(receiverId - nodeID) < closestGap) {
                closest = nodeID;
                closestGap = Math.abs(nodeID - closest);
            }
        }
        return closest;
    }
}
