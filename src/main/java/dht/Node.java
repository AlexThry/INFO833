package dht;

import simulator.Event;
import simulator.GlobalNode;
import simulator.Message;

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
