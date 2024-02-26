package simulator;

import DHT.Node;
import org.w3c.dom.traversal.NodeIterator;

public class EventHandler {
    protected Node node;

    protected Simulator simulator = Simulator.getInstance();

    public EventHandler(Node node) {
        this.node = node;
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
        }
    }

    public void joinRequestHandler(Event event) {

        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();
        Integer nodeID = this.node.ID;
        Network network = Network.getInstance();

        boolean insertRight = (senderID > nodeID && nodeID > node.right) || (senderID > nodeID && senderID < node.right);
        boolean insertLeft = (senderID < nodeID && nodeID < node.left) || (senderID < nodeID && senderID > node.left);

        if (insertLeft) {
            Logger.log("JOIN", "NEW JOIN ON " + node.left + " for " + senderID, simulator.getTime());
            simulator.addEvent(new Event(senderID, senderIP, node.left, new Message(Message.JOIN), simulator.calculateEventArrivalTime()));
            node.setLeft(senderID);
            network.getNodeByIP(senderIP).setRight(nodeID);
        } else if (insertRight) {
            Logger.log("JOIN", "NEW JOIN ON " + node.right + " for " + senderID, simulator.getTime());
            simulator.addEvent(new Event(senderID, senderIP, node.right, new Message(Message.JOIN), simulator.calculateEventArrivalTime()));
            node.setRight(senderID);
            network.getNodeByIP(senderIP).setLeft(nodeID);
        } else {
            Integer closest = getClosestRouter(event);
            Logger.log("JOIN_REQUEST", "NEW JOIN_REQUEST ON " + closest + " for " + senderID, simulator.getTime());
            simulator.addEvent(new Event(senderID, senderIP, closest, new Message(Message.JOIN_REQUEST), simulator.calculateEventArrivalTime()));
        }
        simulator.removeEvent();
    }

    public void joinHandler(Event event) {
        Integer senderID = event.getSenderID();
        Integer senderIP = event.getSenderIP();
        Integer nodeID = node.getID();
        Network network = Network.getInstance();

        if (senderID > node.getID()) {
            Logger.log("JOIN", "NEW JOIN ON " + node.right + " for " + senderID, simulator.getTime());
            node.setRight(senderID);
            network.getNodeByIP(senderIP).setLeft(nodeID);

        } else {
            Logger.log("JOIN", "NEW JOIN ON " + node.left + " for " + senderID, simulator.getTime());
            node.setLeft(senderID);
            network.getNodeByIP(senderIP).setRight(nodeID);

        }
    }

    public void joinAckHandler(Event event) {

    }

    public Integer getClosestRouter(Event event) {
        // Initialisation
        Integer closest = node.right;

        if (Math.abs(node.getID() - node.left) < Math.abs(node.getID() - closest)) {
            closest = node.left;
        }

        for (Integer nodeID: node.knownNodes) {
            if (Math.abs(node.getID() - nodeID) < Math.abs(node.getID() - closest)) {
                closest = nodeID;
            }
        }

        return closest;
    }
}
