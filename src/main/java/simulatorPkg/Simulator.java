package simulatorPkg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Simulator {
    private static ArrayList<Event> events = new ArrayList<>();
    private static Simulator instance;
    private static Integer time = 0;
    private static Network network = Network.getInstance();
    protected Simulator() {}

    //
    // Get Simulator instance
    //

    public static synchronized Simulator getInstance() {
        if (instance == null) {
            instance = new Simulator();
        }
        return instance;
    }

    //
    // Adding event
    //

    public static synchronized void addEvent(EventType type, Integer senderId, Integer receiverId, Integer routerId, Integer duration) {
        // Création de l'évènement
        Event event = new Event(type, senderId, receiverId, routerId, time + duration);
        // Ajout de l'évènement à la liste d'events
        events.add(event);
        // Tri de la liste d'events par date d'arrivée
        sortEventsByDuration();
    }

    //
    // Sort events
    //

    public static void sortEventsByDuration() {
        Collections.sort(events, Comparator.comparing(Event::getArrivalTime));
    }

    //
    // Run the simulator
    //

    public static void run() {
        // Time
        while (events.size() > 0) {
            for (Event event: events) {
                // If there is events arriving at time T, send events to receivers
                if (event.getArrivalTime() == time) {
                    Integer receiverId = event.getReceiverId();
                    // Get node from event receiverId
                    Node receiverNode = network.getNode(receiverId);
                    receiverNode.handleEvent(event);
                } else {
                    break;
                }
            }
            time += 1;
        }
    }
}
