package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Simulator {
    private ArrayList<Event> eventList;
    private static Simulator instance;
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
