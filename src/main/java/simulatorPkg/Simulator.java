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

    public synchronized void addEvent(Message message, Integer senderId, Integer senderIP, Integer receiverId, Integer routerId, Integer duration) {
        // Création de l'évènement
        Event event = new Event(message, senderId, senderIP, receiverId, routerId, time + duration);
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
    // Calculate event duration
    //

    public Integer calculateDuration() {
        // Caluler le temps ici
        return 10;
    }

    //
    // Run the simulator
    //

    public static void run() throws InterruptedException {
        // Time
        while (events.size() > 0) {
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                if (event.getArrivalTime() == time) {
                    Integer receiverId = event.getReceiverId();
                    Node receiverNode = network.getNodeByDHTId(receiverId);
                    receiverNode.handleEvent(event);
                    events.remove(event);
                } else {
                    break;
                }
            }
//            Thread.sleep(100);
            time += 1;
        }
    }
}
