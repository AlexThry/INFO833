package simulator;

import dht.Node;

public abstract class GlobalEventHandler {
    protected Node node;

    protected Simulator simulator = Simulator.getInstance();

    public GlobalEventHandler(Node node) {
        this.node = node;
    }


}
