package simulator;

import java.util.ArrayList;

public abstract class Node {
    private ArrayList<Node> knownNodes;
    private Integer DHTId;
    private Integer IP;

    public Node(Integer DHTId, Integer IP) {
        this(new ArrayList<>(), DHTId, IP);
    }

    public Node(ArrayList<Node> knownNodes, Integer DHTId, Integer IP) {
        this.knownNodes = knownNodes;
        this.DHTId = DHTId;
        this.IP = IP;
    }

    public Integer routeEvent(Event event) {

        boolean goRight = event.getSenderDHTId() > this.DHTId;


    }
}
