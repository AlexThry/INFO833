package dht;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import simulator.Event;
import simulator.Message;
import simulator.Network;
import simulator.Simulator;

import static org.junit.jupiter.api.Assertions.*;

class EventHandlerTest {

    Simulator simulator = Simulator.getInstance();
    Network network = Network.getInstance();

    Node node1 = new Node(10, 10);
    Node node2 = new Node(34, 34);
    Node node3 = new Node(50, 50);
    Node node4 = new Node(60, 60);

    @BeforeEach
    void setUp() {
        Simulator.getEventList().clear();
        Network.getNodeList().clear();

        Network.addNode(node1);
        Network.addNode(node2);
        Network.addNode(node3);
        Network.addNode(node4);

        node1.setLeft(60);
        node1.setRight(34);

        node2.setLeft(10);
        node2.setRight(50);

        node3.setLeft(34);
        node3.setRight(60);

        node4.setLeft(50);
        node4.setRight(10);


    }

    @Test
    void joinRequestHandler() {

        Node node5 = new Node(61, 1);
        Node node6 = new Node(26, 2);
        Node node7 = new Node(-1, 3);

        Network.addNode(node5);
        Network.addNode(node6);
        Network.addNode(node7);

        Message joinRequestMessage = new Message(Message.JOIN_REQUEST);

        Event join1 = new Event(61, 1, 34, joinRequestMessage, 1);
        Event join2 = new Event(26, 2, 50, joinRequestMessage, 1);
        Event join3 = new Event(-1, 3, 34, joinRequestMessage, 1);

        Simulator.addEvent(join1);
        Simulator.addEvent(join2);
        Simulator.addEvent(join3);

        Simulator.run();

        assertEquals(60, Network.getNodeByIP(1).getLeft());
        assertEquals(-1, Network.getNodeByIP(1).getRight());

        assertEquals(10, Network.getNodeByIP(2).getLeft());
        assertEquals(34, Network.getNodeByIP(2).getRight());

        assertEquals(61, Network.getNodeByIP(3).getLeft());
        assertEquals(10, Network.getNodeByIP(3).getRight());
    }

    @Test
    void joinHandler() {
    }

    @Test
    void joinAckHandler() {
    }

    @Test
    void leaveRequestHandler() {
    }

    @Test
    void leaveHandler() {
    }

    @Test
    void getClosestRouter() {
    }
}