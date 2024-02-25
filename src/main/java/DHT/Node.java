package DHT;

import simulator.Event;
import simulator.EventHandler;
import simulator.GlobalNode;
import simulator.Message;

import java.util.ArrayList;

public class Node extends GlobalNode {
    public Node(Integer ID, Integer IP) {
        super(ID, IP);
    }

    public Node(ArrayList<Integer> knownNodes, Integer ID, Integer IP) {
        super(knownNodes, ID, IP);
    }

    public Integer getClosestNodeID(Event event) {
        // Permet de récupérer l'id du noeud connu le plus proche du destinataire

        boolean isDestinationKnown = event.getDestinationId() != null;

        Integer closest = this.knownNodes.get(0);

        Integer destinationReferal;

        if (isDestinationKnown) {
            destinationReferal = event.getDestinationId();
        } else {
            destinationReferal = event.getSenderID();
        }

        for (Integer ID: this.knownNodes) {
            if (Math.abs(event.getSenderID() - ID) < closest) {
                closest = Math.abs(destinationReferal - ID);
            }
        }
        return closest;
    }

    public Integer routeEvent(Event event) {

        Integer eventType = event.getMessage().getEVENT_TYPE();

        boolean isJoinRequest = eventType == Message.JOIN_REQUEST;
        boolean isJoin = eventType == Message.JOIN;
        boolean isJoinAck = eventType == Message.JOIN_ACK;

        Integer route = -1;

        boolean isGreaterThanReceived = (this.id > event.getSenderID()) && (route != -1);
        boolean goRight = (Math.abs(event.getSenderID() - this.knownNodes.get(1)) < Math.abs(event.getSenderID() - this.knownNodes.get(0))) && (route != -1);
        boolean isLast = (this.id > this.knownNodes.get(1)) && (route != -1);
        boolean isFirst = (this.id < this.knownNodes.get(0)) && (route != -1);
        boolean isDestinationKnown = event.getDestinationId() != null;
        boolean isDestination = this.id == event.getDestinationId();

        if (isDestinationKnown && isDestination) {
            EventHandler.handleEvent(event);
        }

        // change this result
        return route;
    }
}
