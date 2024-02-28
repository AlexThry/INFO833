package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GlobalNode {
    protected Integer left;
    protected Integer right;
    protected List<Integer> knownNodes;
    protected Integer ID;
    protected Integer IP;
    protected Simulator simulator = Simulator.getInstance();

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
}
