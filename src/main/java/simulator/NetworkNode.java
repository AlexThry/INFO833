package simulator;

import java.util.ArrayList;
import java.util.List;

public class NetworkNode implements Node{
    protected Integer physicalX;
    protected Integer physicalY;
    protected Integer nodeID;
    protected List<NetworkNode> knownNodes = new ArrayList<>();

    public NetworkNode(Integer physicalX, Integer physicalY, Integer nodeID) {
        this.physicalX = physicalX;
        this.physicalY = physicalY;
        this.nodeID = nodeID;
    }

    public Integer getPhysicalX() {
        return physicalX;
    }

    public Integer getPhysicalY() {
        return physicalY;
    }

    public Integer getNodeID() {
        return nodeID;
    }

    public List<NetworkNode> getKnownNodes() {
        return knownNodes;
    }

    public void setPhysicalX(Integer physicalX) {
        this.physicalX = physicalX;
    }

    public void setPhysicalY(Integer physicalY) {
        this.physicalY = physicalY;
    }

    public void setNodeID(Integer nodeID) {
        this.nodeID = nodeID;
    }

    public void setKnownNodes(List<NetworkNode> knownNodes) {
        this.knownNodes = knownNodes;
    }

    @Override
    public String toString() {
        ArrayList<Integer> knownNodesIds = new ArrayList<>();
        for (NetworkNode node: knownNodes) {
            knownNodesIds.add(node.getNodeID());
        }
        return "NetworkNode{" +
                "physicalX=" + physicalX +
                ", physicalY=" + physicalY +
                ", nodeID=" + nodeID +
                ", knownNodes=" + knownNodesIds +
                '}';
    }
}
