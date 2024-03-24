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
    public static final Integer SEND = 5;
    public static final Integer FORWARD = 6;
    public static final Integer DELIVER = 7;

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
            case -1 -> "EVENT REFUSED";
            case 0 -> "JOIN REQUEST";
            case 1 -> "JOIN";
            case 2 -> "JOIN ACK";
            case 3 -> "LEAVE REQUEST";
            case 4 -> "LEAVE";
            case 5 -> "SEND";
            case 6 -> "FORWARD";
            case 7 -> "DELIVER";
            default -> "";
        };
    }

    private static String getMessage(Integer eventType, Integer routerID, Integer receiverID, Integer senderID) {

        String eventTypeStr = getEventType(eventType);
        String message = null;

        if (eventTypeStr.equals("DELIVER")) {
            message = "EVENT EXECUTED: " +
                    eventTypeStr +
                    "\nAT TIME: " +
                    simulator.getTime() +
                    "\nSender node: "+
                    senderID +
                    "\nRouter node: " +
                    routerID +
                    "\nDestination node: " +
                    receiverID;
        } else if (eventTypeStr.equals("FORWARD")||eventTypeStr.equals("SEND")) {
            message = "EVENT EXECUTED: " +
                    eventTypeStr +
                    "\nAT TIME: " +
                    simulator.getTime() +
                    "\nSender node: "+
                    senderID +
                    "\nRouter node: " +
                    routerID +
                    "\nDestination node: " +
                    receiverID;
        } else if (eventTypeStr.equals("JOIN") || eventTypeStr.equals("JOIN REQUEST")) {
            message = "EVENT EXECUTED: " +
                    eventTypeStr +
                    "\nAT TIME: " +
                    simulator.getTime() +
                    "\nRouter node: " +
                    routerID +
                    "\nReceiver node: " +
                    receiverID;

            if (senderID != null) {
                message += "\nNode to insert: " +
                        senderID;
            }
        } else if (eventTypeStr.equals("JOIN ACK")) {
            message = "EVENT EXECUTED: " +
                    eventTypeStr +
                    "\nAT TIME: " +
                    simulator.getTime() +
                    "\nJoining node: " +
                    routerID +
                    "\nAck sender: " +
                    receiverID;
        } else if (eventTypeStr.equals("LEAVE REQUEST")) {
            message = "EVENT EXECUTED: " +
                    eventTypeStr +
                    "\nAT TIME: " +
                    simulator.getTime() +
                    "\nNode leaving: " +
                    routerID +
                    "\nNode concerned (neighbor): " +
                    receiverID;
        } else if (eventTypeStr.equals("LEAVE")){
            message = "EVENT EXECUTED: " +
                    eventTypeStr +
                    "\nAT TIME: " +
                    simulator.getTime() +
                    "\nNode concerned (old neighbor): " +
                    routerID +
                    "\nNode leaving : " +
                    receiverID;
        }else if (eventTypeStr.equals("EVENT REFUSED")) {
            message = "EVENT EXECUTED: " +
                    eventTypeStr +
                    "\nAT TIME: " +
                    simulator.getTime();
        }

        message += "\n----------\n";
        return message;
    }

    private  static String getMessage(Integer eventType, Integer routerID, Integer receiverID) {
        return getMessage(eventType, routerID, receiverID, null);
    }
}
