package dht;

import simulator.*;
import simulator.MessagesObjects.LeaveObject;

import java.util.ArrayList;

public class Node extends GlobalNode {
    public final EventHandler eventHandler = new EventHandler(this);
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

    public void leave() {

        LeaveObject leaveObject = new LeaveObject(left, right);
        Message leaveMessage = new Message(Message.LEAVE, leaveObject);

        Event leaveLeft = new Event(ID, IP, left, leaveMessage, Simulator.calculateEventArrivalTime(ID, left));
        Event leaveRight = new Event(ID, IP, left, leaveMessage, Simulator.calculateEventArrivalTime(ID, right));


        Simulator.addEvent(leaveLeft);
        Simulator.addEvent(leaveRight);
    }

    @Override
    public String toString() {
        return "Node{" +
                "left=" + left +
                ", right=" + right +
                ", knownNodes=" + knownNodes +
                ", ID=" + ID +
                ", networkNodeId=" + networkNodeId +
                '}';
    }
}
