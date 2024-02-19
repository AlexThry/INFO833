package simulator;

import java.util.ArrayList;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;

public abstract class Node {
    private ArrayList<Integer> knownNodes;
    private Integer DHTId;
    private Integer IP;

    public Node(Integer DHTId, Integer IP) {
        this(new ArrayList<>(), DHTId, IP);
    }

    public Node(ArrayList<Integer> knownNodes, Integer DHTId, Integer IP) {
        this.knownNodes = knownNodes;
        this.DHTId = DHTId;
        this.IP = IP;
    }

    // Si l'evenement

    public Integer routeEvent(Event event) {

        Integer eventType = event.getMessage().getEVENT_TYPE();

        boolean goRight = event.getSenderDHTId() > this.DHTId;
        boolean isLast = this.DHTId > this.knownNodes.get(1);
        boolean isFirst = this.DHTId < this.knownNodes.get(0);

        if (goRight && isLast) {

        }
    }
}
