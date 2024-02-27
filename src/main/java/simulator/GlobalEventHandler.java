package simulator;

import DHT.Logger;
import DHT.Node;
import simulator.MessagesObjects.LeaveObject;

public class GlobalEventHandler {
    protected Node node;

    protected Simulator simulator = Simulator.getInstance();

    public GlobalEventHandler(Node node) {
        this.node = node;
    }


}
