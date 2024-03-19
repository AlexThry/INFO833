package simulator;

import java.util.*;

public class Network {
    private static Network instance;
    private static List<GlobalNode> nodeList = new ArrayList<>();
    private static List<NetworkNode> networkNodesList = new ArrayList<>();

    public static synchronized Network getInstance() {
        if (instance == null) {
            instance = new Network();
        }
        return instance;
    }

    public static void addNode(GlobalNode node) {
        Random rand = new Random();
        nodeList.add(node);
        node.setPhysicalX(rand.nextInt(10000));
        node.setPhysicalY(rand.nextInt(10000));
        nodeList.sort(Comparator.comparing(GlobalNode::getID));
        addNetworkToNode(node);
    }

    public static void addNetworkToNode(GlobalNode node) {
        Integer limitDisance = 1000;
        NetworkNode closestNetworkNode = getClosestNetworkNode(node);
        if (getDistance(closestNetworkNode, node) < limitDisance) {
            node.setNetworkNodeId(closestNetworkNode.getNodeID());
        } else {
            Integer newID = networkNodesList.size();
            NetworkNode networkNode = new NetworkNode(node.physicalX, node.getPhysicalY(), newID);
            node.setNetworkNodeId(networkNode.getNodeID());
            addNetworkNode(networkNode);
        }
    }

    public static GlobalNode getRandomDHTNode(Integer nodeID) {
        List<GlobalNode> eligibleNodes = new ArrayList<>();
        for (GlobalNode node : nodeList) {
            if (node.getRight() != null && node.getLeft() != null && node.getID() != nodeID) {
                eligibleNodes.add(node);
            }
        }

        if (eligibleNodes.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        return eligibleNodes.get(rand.nextInt(eligibleNodes.size()));
    }

    public static void addNetworkNode(NetworkNode node) {
        Integer limitDistance = 1000;
        networkNodesList.add(node);
        for (NetworkNode networkNode: networkNodesList) {
            // Ajouter dans tous les noeuds les noeuds proches
            if (node != networkNode) {
                Double distance = getDistance(node, networkNode);
                if (distance <= limitDistance && networkNode != node) {
                    networkNode.getKnownNodes().add(node);
                    node.getKnownNodes().add(networkNode);
                }
            }
        }
        if (node.getKnownNodes().isEmpty()) {
            NetworkNode closest = getClosestNetworkNode(node);
            if (closest != node) {
                node.getKnownNodes().add(closest);
                closest.getKnownNodes().add(node);
            }
        }
    }

    public static GlobalNode getNodeByID(Integer ID) {
        for (GlobalNode node : nodeList) {
            if (node.getID().equals(ID)) {
                return node;
            }
        }
        return null;
    }

    public static NetworkNode getNetworkNodeByID(Integer ID) {
        for (NetworkNode node : networkNodesList) {
            if (node.getNodeID().equals(ID)) {
                return node;
            }
        }
        return null;
    }

    public static Double getDistance(Node node1, Node node2) {
        return Math.sqrt(Math.pow((node1.getPhysicalX() - node2.getPhysicalX()), 2) + Math.pow((node1.getPhysicalY() - node2.getPhysicalY()), 2));
    }

    public static NetworkNode getClosestNetworkNode(Node node) {
        NetworkNode returnNode;

        if (networkNodesList.isEmpty()) {
            returnNode = new NetworkNode(node.getPhysicalX(), node.getPhysicalY(), 0);
            Network.addNetworkNode(returnNode);
        } else {
            Double closestDistance = getDistance(node, networkNodesList.get(0));
            returnNode = networkNodesList.get(0);

            for (NetworkNode networkNode: networkNodesList) {

                Double distance = getDistance(node, networkNode);
                boolean isCloser = distance < closestDistance && networkNode != node;

                if (isCloser) {
                    closestDistance = distance;
                    returnNode = networkNode;
                }
            }
        }

        return returnNode;
    }

    public static Integer calculateArrivalTime(Integer currentNodeID, Integer routerNodeID) {
        GlobalNode routerNode = getNodeByID(routerNodeID);
        GlobalNode currentNode = getNodeByID(currentNodeID);

        NetworkNode routerNetworkNode = getNetworkNodeByID(routerNode.getNetworkNodeId());
        NetworkNode currentNetworkNode = getNetworkNodeByID(currentNode.getNetworkNodeId());

        Integer duration;

        if (routerNetworkNode == currentNetworkNode) {
            duration = 2;
        } else {
            duration = getMinNumberOfNetworkNodes(currentNode, routerNode) + 2;
        }
        return duration;
    }


    public static Integer getMinNumberOfNetworkNodes(GlobalNode startNode, GlobalNode endNode) {
        Set<NetworkNode> visited = new HashSet<>();
        return dfs(startNode.getNetworkNodeId(), endNode.getNetworkNodeId(), visited);
    }

    private static Integer dfs(Integer startNodeId, Integer endNodeId, Set<NetworkNode> visited) {
        if (startNodeId.equals(endNodeId)) {
            return 0;
        }

        NetworkNode startNode = getNetworkNodeByID(startNodeId);
        visited.add(startNode);

        Integer minNumberOfNodes = Integer.MAX_VALUE;
        for (NetworkNode neighbor : startNode.getKnownNodes()) {
            if (!visited.contains(neighbor)) {
                Integer numberOfNodes = dfs(neighbor.getNodeID(), endNodeId, visited);
                if (numberOfNodes != null) {
                    minNumberOfNodes = Math.min(minNumberOfNodes, numberOfNodes + 1);
                }
            }
        }

        visited.remove(startNode);
        return minNumberOfNodes == Integer.MAX_VALUE ? null : minNumberOfNodes;
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

    public static List<NetworkNode> getNetworkNodesList() {
        return networkNodesList;
    }

    @Override
    public String toString() {
        return "Network{" +
                "nodeList=" + nodeList +
                '}';
    }
}
