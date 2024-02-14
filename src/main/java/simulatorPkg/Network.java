package simulatorPkg;

import java.util.ArrayList;

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
        if (this.getNode(node.getId()) == null) {
            this.nodes.add(node);
        } else {
            System.out.println("Node already exist");
        }
    }

    public Node getNode(Integer nodeId) {
        for (Node node: nodes) {
            if (node.getId() == nodeId) {
                return node;
            }
        }
        System.out.println("Node doesn't exist in network");
        return null;
    }

    public void removeNode(Integer nodeId) {
        for (Node node: nodes) {
            if (node.getId() == nodeId) {
                nodes.remove(node);
            }
        }
    }
}
