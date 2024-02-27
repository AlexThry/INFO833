package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GlobalNode {
    protected Integer left;
    protected Integer right;
    protected List<Integer> knownNodes;
    protected Integer ID;
    protected Integer IP;
    protected Simulator simulator = Simulator.getInstance();

    public GlobalNode(Integer ID, Integer IP) {
        this(new ArrayList<>(), ID, IP);
    }

    public GlobalNode(ArrayList<Integer> knownNodes, Integer ID, Integer IP) {
        this.knownNodes = knownNodes;
        this.ID = ID;
        this.IP = IP;
    }
    
    public void addKnownNode(Integer ID) {
        this.knownNodes.add(ID);
        Collections.sort(this.knownNodes);
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public abstract void handleEvent(Event event);

    public Integer getID() {
        return ID;
    }

    public Integer getIP() {
        return IP;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public List<Integer> getKnownNodes() {
        return knownNodes;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getRight() {
        return right;
    }

    //    public void joinRoute(Integer receiverId, Integer senderId, Integer senderIP, Message message) {
//        Message join = new Message("JOIN", 1);
//        if (senderId > this.ID && this.ID > this.right) {
//            // On prévient l'autre noeud d'ajouter le nouveau noeud en voisin
//            getSimulator().addEvent(join, senderId, senderIP, this.right, this.ID, getSimulator().calculateDuration());
//            this.setRight(senderId);
//            getNetwork().getNodeByIP(senderIP).setLeft(this.ID);
//        } else if (senderId < this.ID && this.ID < this.left) {
//            getSimulator().addEvent(join, senderId, senderIP, this.left, this.ID, getSimulator().calculateDuration());
//            this.setLeft(senderId);
//            getNetwork().getNodeByIP(senderIP).setRight(this.ID);
//        } else if (Math.abs(senderId - this.right) < Math.abs(senderId - this.left)) {
//            if (senderId < this.right) {
//                getSimulator().addEvent(join, senderId, senderIP, this.right, this.ID, getSimulator().calculateDuration());
//                this.setRight(senderId);
//                getNetwork().getNodeByIP(senderIP).setLeft(this.ID);
//            } else {
//                getSimulator().addEvent(message, senderId, senderIP, this.right, this.ID, getSimulator().calculateDuration());
//            }
//        } else {
//            if (senderId > this.left) {
//                getSimulator().addEvent(join, senderId, senderIP, this.left, this.ID, getSimulator().calculateDuration());
//                this.setLeft(senderId);
//                getNetwork().getNodeByIP(senderIP).setRight(this.ID);
//            } else {
//                getSimulator().addEvent(message, senderId, senderIP, this.left, this.ID, getSimulator().calculateDuration());
//            }
//        }
//    }
}
