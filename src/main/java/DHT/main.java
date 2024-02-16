package DHT;

import simulatorPkg.Message;
import simulatorPkg.Network;
import simulatorPkg.Simulator;


public class main {
    public static void main(String[] args) throws InterruptedException {

        Simulator simulator = Simulator.getInstance();
        Network network = Network.getInstance();

        Message message1 = new Message("JOINREQUEST", 0);

        Integer newNodeId = -1;

        simulatorPkg.Node node0 = new DHTNode(25, 5, 0, 10);
        simulatorPkg.Node node5 = new DHTNode(0, 10, 5, 2);
        simulatorPkg.Node node10 = new DHTNode(5, 15, 10, 1);
        simulatorPkg.Node node15= new DHTNode(10, 20, 15, 14);
        simulatorPkg.Node node20= new DHTNode(15, 25, 20, 3);
        simulatorPkg.Node node25= new DHTNode(20, 0, 25, 19);
        simulatorPkg.Node newNode = new DHTNode(newNodeId, 45);

        network.addNode(node0);
        network.addNode(node5);
        network.addNode(node10);
        network.addNode(node15);
        network.addNode(node20);
        network.addNode(node25);
        network.addNode(newNode);


        simulator.addEvent(message1, newNodeId, 45, network.getRandomNode().getDHTid(), newNodeId, simulator.calculateDuration());

        simulator.run();

        System.out.println(network);
    }
}