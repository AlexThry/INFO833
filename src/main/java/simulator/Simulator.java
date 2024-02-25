package simulator;

import DHT.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        while (eventList.size() != 0) {
            System.out.println("time: " + this.time);
            for (int i = 0; i < eventList.size(); i++) {
                Event event = eventList.get(i);
                if (event.getArrivalTime() == this.time) {
                    Integer destinationId = event.getDestinationId();
                    Node destinationNode = (Node) network.getNodeByID(destinationId);
                    destinationNode.handleEvent(event);
                    eventList.remove(event);
                    System.out.println(eventList);
                } else {
                    break;
                }
            }
            this.time++;
        }
    }

    public void addEvent(Event event) {
        this.calculateEventDuration(event);
        this.eventList.add(event);
        this.sortEvents();
    }

    public void sortEvents() {
        Collections.sort(eventList, Comparator.comparing(Event::getArrivalTime));
    }

    public void calculateEventDuration(Event event) {
        // TODO: calculate time
        event.setArrivalTime(this.time + 10);
    }
}
