package DHT;

import simulatorPkg.Event;
import simulatorPkg.Message;
import simulatorPkg.Node;

public class DHTNode extends simulatorPkg.Node {
    public DHTNode(Integer left, Integer right, Integer id, Integer IP) {
        super(left, right, id, IP);
    }

    public DHTNode(Integer id, Integer IP) {
        super(id, IP);
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println(event);
        switch (event.getMessage().getType()) {
            case (0) -> this.route(event);
            case (1) -> this.joinNode(event);
            case (2) -> this.leaveNode(event);
            default -> {
                return;
            }
        }
    }

    @Override
    public void route(Event event) {
        Integer receiverId = event.getReceiverId();
        Integer senderId = event.getSenderId();
        Integer senderIP = event.getSenderIP();
        Message message = event.getMessage();

        if (event.getMessage().getType() == 0) {
            joinRoute(receiverId, senderId, senderIP, message);
        }
    }

    public void joinRoute(Integer receiverId, Integer senderId, Integer senderIP, Message message) {
        Message join = new Message("JOIN", 1);
        if (senderId > this.DHTid && this.DHTid > this.right) {
            // On prévient l'autre noeud d'ajouter le nouveau noeud en voisin
            getSimulator().addEvent(join, senderId, senderIP, this.right, this.DHTid, getSimulator().calculateDuration());
            this.setRight(senderId);
            getNetwork().getNodeByIP(senderIP).setLeft(this.DHTid);
        } else if (senderId < this.DHTid && this.DHTid < this.left) {
            getSimulator().addEvent(join, senderId, senderIP, this.left, this.DHTid, getSimulator().calculateDuration());
            this.setLeft(senderId);
            getNetwork().getNodeByIP(senderIP).setRight(this.DHTid);
        } else if (Math.abs(senderId - this.right) < Math.abs(senderId - this.left)) {
            if (senderId < this.right) {
                getSimulator().addEvent(join, senderId, senderIP, this.right, this.DHTid, getSimulator().calculateDuration());
                this.setRight(senderId);
                getNetwork().getNodeByIP(senderIP).setLeft(this.DHTid);
            } else {
                getSimulator().addEvent(message, senderId, senderIP, this.right, this.DHTid, getSimulator().calculateDuration());
            }
        } else {
            if (senderId > this.left) {
                getSimulator().addEvent(join, senderId, senderIP, this.left, this.DHTid, getSimulator().calculateDuration());
                this.setLeft(senderId);
                getNetwork().getNodeByIP(senderIP).setRight(this.DHTid);
            } else {
                getSimulator().addEvent(message, senderId, senderIP, this.left, this.DHTid, getSimulator().calculateDuration());
            }
        }
    }

    public void joinNode(Event event) {
        Integer senderId = event.getSenderId();
        Integer senderIP = event.getSenderIP();
        if (senderId > this.DHTid && senderId > this.right) {
            this.setLeft(senderId);
            getNetwork().getNodeByIP(senderIP).setRight(this.DHTid);
        } else if (senderId < this.DHTid && senderId < this.left) {
            this.setRight(senderId);
            getNetwork().getNodeByIP(senderIP).setLeft(this.DHTid);
        } else if (senderId > this.DHTid) {
            this.setRight(senderId);
            getNetwork().getNodeByIP(senderIP).setLeft(this.DHTid);
        } else {
            this.setLeft(senderId);
            getNetwork().getNodeByIP(senderIP).setRight(this.DHTid);
        }
    }
}
