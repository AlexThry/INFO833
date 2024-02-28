package simulator;

import java.util.*;

public class Network {
    private static Network instance;
    private static List<GlobalNode> nodeList;

    private Network() {
        this.nodeList = new ArrayList<>();
    }

    public static synchronized Network getInstance() {
        if (instance == null) {
            instance = new Network();
        }
        return instance;
    }

    public static void addNode(GlobalNode node) {
        nodeList.add(node);
        nodeList.sort(Comparator.comparing(GlobalNode::getID));
    }

    public static GlobalNode getNodeByID(Integer ID) {
        for (GlobalNode node : nodeList) {
            if (node.getID().equals(ID)) {
                return node;
            }
        }
        return null;
    }

    public static GlobalNode getNodeByIP(Integer IP) {
        for (GlobalNode node : nodeList) {
            if (node.getIP().equals(IP)) {
                return node;
            }
        }
        return null;
    }

    public static void removeNode(GlobalNode node) {
        nodeList.remove(node);
    }

    public static List<GlobalNode> getNodeList() {
        return nodeList;
    }



    @Override
    public String toString() {
        return "Network{" +
                "nodeList=" + nodeList +
                '}';
    }
}
