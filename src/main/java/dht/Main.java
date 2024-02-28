package dht;

import simulator.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Network network = Network.getInstance();
        Simulator simulator = Simulator.getInstance();

        Node node1 = new Node(10, 10);
        Node node2 = new Node(34, 34);
        Node node3 = new Node(50, 50);
        Node node4 = new Node(60, 60);
        Node node5 = new Node(61, 61);
        Node node6 = new Node(26, 26);
        Node node7 = new Node(59, 59);

        network.addNode(node1);
        network.addNode(node2);
        network.addNode(node3);
        network.addNode(node4);
        network.addNode(node5);
        network.addNode(node6);
        network.addNode(node7);

        node1.setLeft(60);
        node1.setRight(34);

        node2.setLeft(10);
        node2.setRight(50);

        node3.setLeft(34);
        node3.setRight(60);

        node4.setLeft(50);
        node4.setRight(10);


        Message joinRequestMessage = new Message(Message.JOIN_REQUEST);

        Event join1 = new Event(61, 61, 34, joinRequestMessage, 1);
        Event join2 = new Event(26, 26, 50, joinRequestMessage, 1);
        Event join3 = new Event(59, 59, 34, joinRequestMessage, 6);

        Message leaveRequestMessage1 = new Message(Message.LEAVE_REQUEST);

        Event leaveRequest1 = new Event(null, null, 10, leaveRequestMessage1, 10);
        Event leaveRequest2 = new Event(null, null, 34, leaveRequestMessage1, 10);

        simulator.addEvent(join1);
        simulator.addEvent(join2);
        simulator.addEvent(join3);
        simulator.addEvent(leaveRequest1);
        simulator.addEvent(leaveRequest2);

        Simulator.run();

       Logger.log(network.getNodeList());

    }
}
