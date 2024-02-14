package DHT;

import simulatorPkg.Event;

public class Node extends simulatorPkg.Node {
    public Node(Integer left, Integer right, Integer id) {
        super(left, right, id);
    }

    public Node(Integer id) {
        super(id);
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getType()) {
            case JOIN -> route(event);
        }
    }

    public void route(Event event) {
        Integer senderId = event.getSenderId();
        Integer arrivalTime = event.getArrivalTime();
        if (senderId > this.id) {
            if (senderId > this.right) {
                simulator.addEvent(simulatorPkg.EventType.JOINREQUEST, senderId, this.right, this.id, arrivalTime);
            } else {
                this.right = senderId;
                simulator.addEvent(simulatorPkg.EventType.JOIN, senderId, this.right, this.id, arrivalTime);
            }
        }
    }
}
