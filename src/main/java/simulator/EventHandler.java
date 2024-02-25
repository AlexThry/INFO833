package simulator;

public class EventHandler {

    public static void handleEvent(Event event) {
        switch (event.getMessage().getEVENT_TYPE()) {
            case 0: joinRequestHandler(event);
            case 1: joinHandler(event);
            case 2: joinAckHandler(event);
        }
    }

    public static void joinRequestHandler(Event event) {}

    public static void joinHandler(Event event) {}

    public static void joinAckHandler(Event event) {}


}
