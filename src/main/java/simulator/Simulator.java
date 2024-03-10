package simulator;

import java.util.*;

public class Simulator {
    private static List<Event> eventList = new ArrayList<>();;
    private static Simulator instance;
    private static final Network network = Network.getInstance();
    private static Integer time = 0;

    public static synchronized Simulator getInstance() {
        if (instance == null) {
            instance = new Simulator();
        }
        return instance;
    }

    public static void run(Integer delay) throws InterruptedException {
        // Time
        while (!eventList.isEmpty()) {
            while (!eventList.isEmpty()) {
                Event event = eventList.get(0);
                if (Objects.equals(event.getArrivalTime(), time)) {
                    network.getNodeByID(event.getRouterID()).handleEvent(event);
                    removeEvent();
                } else {
                    break;
                }
            }

            if (delay != null) {
                Thread.sleep(delay);
            }
            time += 1;
        }
    }

    public static void removeEvent() {
        eventList.remove(0);
    }

    public static void addEvent(Event event) {
        eventList.add(event);
        eventList.sort(Comparator.comparing(Event::getArrivalTime));
    }

    public static Integer calculateEventArrivalTime(Integer currentNodeID, Integer routerNodeID) {
        return time + Network.calculateArrivalTime(currentNodeID, routerNodeID);
//        return time+2;
    }

    public Integer getTime() {
        return time;
    }

    public static List<Event> getEventList() {
        return eventList;
    }
}
