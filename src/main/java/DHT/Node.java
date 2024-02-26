package DHT;

import simulator.Event;
import simulator.EventHandler;
import simulator.GlobalNode;
import simulator.Message;

import java.util.ArrayList;

public class Node extends GlobalNode {
    public EventHandler eventHandler = new EventHandler(this);
    public Node(Integer ID, Integer IP) {
        super(ID, IP);
    }

    public Node(ArrayList<Integer> knownNodes, Integer ID, Integer IP) {
        super(knownNodes, ID, IP);
    }

    @Override
    public void handleEvent(Event event) {
        eventHandler.handleEvent(event);
    }

    public Integer routeEvent(Event event) {

        Integer eventType = event.getMessage().getEVENT_TYPE();

        boolean isJoinRequest = eventType == Message.JOIN_REQUEST;
        boolean isJoin = eventType == Message.JOIN;
        boolean isJoinAck = eventType == Message.JOIN_ACK;

        Integer route = -1;

        boolean isGreaterThanReceived = (this.ID > event.getSenderID()) && (route != -1);
        boolean goRight = (Math.abs(event.getSenderID() - this.knownNodes.get(1)) < Math.abs(event.getSenderID() - this.knownNodes.get(0))) && (route != -1);
        boolean isLast = (this.ID > this.knownNodes.get(1)) && (route != -1);
        boolean isFirst = (this.ID < this.knownNodes.get(0)) && (route != -1);
        boolean isDestinationKnown = event.getDestinationId() != null;
        boolean isDestination = this.ID == event.getDestinationId();

        if (isDestinationKnown && isDestination) {
            eventHandler.handleEvent(event);
        }

        // change this result
        return route;
    }

    @Override
    public String toString() {
        return "Node{" +
                "left=" + left +
                ", right=" + right +
                ", knownNodes=" + knownNodes +
                ", ID=" + ID +
                ", IP=" + IP +
                '}';
    }
}
