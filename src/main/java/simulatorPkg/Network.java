package simulatorPkg;

import java.util.ArrayList;
import java.util.Random;

public class Network {
    private ArrayList<Node> nodes = new ArrayList<>();
    private static Network instance;

    protected Network() {}

    public static synchronized Network getInstance() {
        if (instance == null) {
            instance = new Network();
        }
        return instance;
    }

    public void addNode(Node node) {
        if (this.getNodeByDHTId(node.getDHTid()) == null) {
            this.nodes.add(node);
        } else {
            System.out.println("Node already exist");
        }
    }

    public Node getNodeByDHTId(Integer nodeId) {
        for (Node node: nodes) {
            if (node.getDHTid() == nodeId) {
                return node;
            }
        }
        System.out.println("Node doesn't exist in network");
        return null;
    }

    public Node getNodeByIP(Integer nodeIP) {
        for (Node node: nodes) {
            if (node.getIP() == nodeIP) {
                return node;
            }
        }
        System.out.println("Node doesn't exist in network");
        return null;
    }

    public Node getRandomNode() {
        if (nodes.isEmpty()) {
            System.out.println("No nodes in the network");
            return null;
        }
        Random rand = new Random();
        return nodes.get(rand.nextInt(nodes.size()));
    }

    public void removeNode(Integer nodeId) {
        for (Node node: nodes) {
            if (node.getDHTid() == nodeId) {
                nodes.remove(node);
            }
        }
    }

    @Override
    public String toString() {
        return "Network{" +
                "nodes=" + nodes +
                '}';
    }
}
