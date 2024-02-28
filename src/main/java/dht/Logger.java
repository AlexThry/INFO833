package dht;

import simulator.Simulator;

public class Logger {
    private static Logger instance;
    private static final Simulator simulator = Simulator.getInstance();

    public static final Integer JOIN_REQUEST = 0;
    public static final Integer JOIN = 1;
    public static final Integer JOIN_ACK = 2;
    public static final Integer LEAVE_REQUEST = 3;
    public static final Integer LEAVE = 4;

    private Logger() {
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public static void log(Integer eventType, Integer routerID, Integer receiverID, Integer senderID) {
        System.out.println(getMessage(eventType, routerID, receiverID, senderID));
    }

    public static void log(Integer eventType, Integer routerID, Integer receiverID) {
        log(eventType, routerID, receiverID, null);
    }

    public static void log(Object object) {
        System.out.println(object);
    }

    private static String getEventType(Integer eventType) {
        return switch (eventType) {
            case 0 -> "JOIN REQUEST";
            case 1 -> "JOIN";
            case 2 -> "JOIN ACK";
            case 3 -> "LEAVE REQUEST";
            case 4 -> "LEAVE";
            //...
            default -> "";
        };
    }

    private static String getMessage(Integer eventType, Integer routerID, Integer receiverID, Integer senderID) {

        String eventTypeStr = getEventType(eventType);

        String message = "EVENT EXECUTED: " +
                eventTypeStr +
                "\nAT TIME: " +
                simulator.getTime() +
                "\nFROM NODE: " +
                routerID +
                "\nTO NODE: " +
                receiverID;

        if (senderID != null) {
            message += "\nFOR NODE: " +
                            senderID;
        }

        message += "\n----------";
        return message;
    }

    private  static String getMessage(Integer eventType, Integer routerID, Integer receiverID) {
        return getMessage(eventType, routerID, receiverID, null);
    }
}

