package simulator;

public class Logger {
    private static Logger instance;
    private static Simulator simulator = Simulator.getInstance();

    public static Integer JOIN_REQUEST = 0;
    public static Integer JOIN = 1;
    public static Integer JOIN_ACK = 2;

    private Logger() {
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public static void log(Integer EVENT_TYPE, Integer routerID, Integer receiverID, Integer senderID) {
        System.out.println(getMessage(EVENT_TYPE, routerID, receiverID, senderID));
    }

    public static void log(Integer EVENT_TYPE, Integer routerID, Integer receiverID) {
        log(EVENT_TYPE, routerID, receiverID, null);
    }

    private static String getEventType(Integer EVENT_TYPE) {
        String eventType = null;
        switch (EVENT_TYPE) {
            case 0:
                eventType = "JOIN REQUEST";
                break;
            case 1:
                eventType = "JOIN";
                break;
            case 2:
                eventType = "JOIN ACK";
                break;
            //...
            default:
                eventType = "";
                break;
        }
        return eventType;
    }

    private static String getMessage(Integer EVENT_TYPE, Integer routerID, Integer receiverID, Integer senderID) {

        String eventType = getEventType(EVENT_TYPE);

        String message = "EVENT EXECUTED: " +
                eventType +
                "\nAT TIME " +
                simulator.getTime() +
                "\nFROM NODE " +
                routerID +
                "\nTO NODE " +
                receiverID;

        if (senderID != null) {
            message += "\nFOR NODE " +
                            senderID;
        }

        message += "\n----------";
        return message;
    }

    private  static String getMessage(Integer EVENT_TYPE, Integer routerID, Integer receiverID) {
        return getMessage(EVENT_TYPE, routerID, receiverID, null);
    }
}

