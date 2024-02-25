package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GlobalNode {
    protected List<Integer> knownNodes;
    protected Integer id;
    protected Integer IP;

    public GlobalNode(Integer id, Integer IP) {
        this(new ArrayList<>(), id, IP);
    }

    public GlobalNode(ArrayList<Integer> knownNodes, Integer id, Integer IP) {
        this.knownNodes = knownNodes;
        this.id = id;
        this.IP = IP;
    }
    
    public void addKnownNode(Integer ID) {
        this.knownNodes.add(ID);
        Collections.sort(this.knownNodes);
    }

    public Integer getId() {
        return id;
    }

    public Integer getIP() {
        return IP;
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
