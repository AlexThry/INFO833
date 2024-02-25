package simulator;

import DHT.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Simulator {
    private ArrayList<Event> eventList;
    private static Simulator instance;
    private Network network = Network.getInstance();
    private Integer time;

    protected Simulator() {
        this.eventList = new ArrayList<>();
        this.time = 0;
    }

    public synchronized static Simulator getInstance() {
        if (instance == null) {
            instance = new Simulator();
        }
        return instance;
    }

    public void run() {
        while (!eventList.isEmpty()) {
            System.out.println("Time: " + this.time);
            Iterator<Event> eventIterator = eventList.iterator();
            while (eventIterator.hasNext()) {
                Event event = eventIterator.next();
                if (event.getArrivalTime().equals(this.time)) {
                    Integer receiverId = event.getRouterID();
                    Node receiverNode = (Node) network.getNodeByID(receiverId);
                    receiverNode.handleEvent(event);
                    eventIterator.remove();
                }
            }
            this.time++;
        }
    }

    public void addEvent(Event event) {
        this.eventList.add(event);
        Collections.sort(eventList, Comparator.comparing(Event::getArrivalTime));
    }

    public Integer calculateEventArrivalTime() {
        // TODO: calculate time
        return (this.time + 10);
    }

    public Integer getTime() {
        return time;
    }
}
