package simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Network {
    private static Network instance;
    private List<GlobalNode> nodeList;

    private Network() {
        this.nodeList = new ArrayList<>();
    }

    public static synchronized Network getInstance() {
        if (instance == null) {
            instance = new Network();
        }
        return instance;
    }

    public void addNode(GlobalNode node) {
        this.nodeList.add(node);
    }

    public GlobalNode getNodeByID(Integer ID) {
        for (GlobalNode node : this.nodeList) {
            if (node.getID().equals(ID)) {
                return node;
            }
        }
        return null;
    }

    public GlobalNode getNodeByIP(Integer IP) {
        for (GlobalNode node : this.nodeList) {
            if (node.getIP().equals(IP)) {
                return node;
            }
        }
        return null;
    }

    public GlobalNode getRandomNode() {
        if (this.nodeList.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(this.nodeList.size());
        return this.nodeList.get(randomIndex);
    }

    public void removeNode(GlobalNode node) {
        this.nodeList.remove(node);
    }

    public List<GlobalNode> getNodeList() {
        return nodeList;
    }

    @Override
    public String toString() {
        return "Network{" +
                "nodeList=" + nodeList +
                '}';
    }
}
