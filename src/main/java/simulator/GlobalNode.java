package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GlobalNode implements Node {
    protected Integer left;
    protected Integer right;
    protected List<Integer> knownNodes;
    protected Integer ID;
    protected Integer IP;
    protected Simulator simulator = Simulator.getInstance();
    protected Integer physicalX;
    protected Integer physicalY;
    protected Integer networkNodeId;

    protected GlobalNode(Integer ID, Integer IP) {
        this(new ArrayList<>(), ID, IP);
    }

    protected GlobalNode(ArrayList<Integer> knownNodes, Integer ID, Integer IP) {
        this.knownNodes = knownNodes;
        this.ID = ID;
        this.IP = IP;
    }
    
    public void addKnownNode(Integer ID) {
        this.knownNodes.add(ID);
        Collections.sort(this.knownNodes);
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public abstract void handleEvent(Event event);

    public Integer getID() {
        return ID;
    }

    public Integer getIP() {
        return IP;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public List<Integer> getKnownNodes() {
        return knownNodes;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getRight() {
        return right;
    }

    public Integer getPhysicalX() {
        return physicalX;
    }

    public Integer getPhysicalY() {
        return physicalY;
    }

    public void setPhysicalX(Integer physicalX) {
        this.physicalX = physicalX;
    }

    public void setPhysicalY(Integer physicalY) {
        this.physicalY = physicalY;
    }

    public Integer getNetworkNodeId() {
        return networkNodeId;
    }

    public void setNetworkNodeId(Integer networkNodeId) {
        this.networkNodeId = networkNodeId;
    }
}
