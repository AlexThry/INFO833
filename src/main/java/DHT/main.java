package DHT;

import simulator.Event;
import simulator.Message;
import simulator.Network;
import simulator.Simulator;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {

        Network network = Network.getInstance();
        Simulator simulator = Simulator.getInstance();

        Node node1 = new Node(10, 10);
        Node node2 = new Node(34, 34);
        Node node3 = new Node(50, 50);
        Node node4 = new Node(60, 60);
        Node node5 = new Node(24, 24);
        Node node6w = new Node(26, 26);

        network.addNode(node1);
        network.addNode(node2);
        network.addNode(node3);
        network.addNode(node4);

        node1.addKnownNode(34);
        node1.addKnownNode(60);
        node2.addKnownNode(10);
        node2.addKnownNode(50);
        node3.addKnownNode(34);
        node3.addKnownNode(60);
        node4.addKnownNode(50);
        node4.addKnownNode(10);

        Message joinMessage = new Message(Message.JOIN_REQUEST);

        Event event1 = new Event(24, 24, 24, joinMessage, 34, 10);
        Event event2 = new Event(26, 26, 26, joinMessage, 60, 10);

        simulator.addEvent(event1);
        simulator.addEvent(event2);

        simulator.run();

    }
}
