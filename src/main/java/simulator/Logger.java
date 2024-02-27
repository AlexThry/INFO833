package simulator;

public class Logger {
    private static Logger instance;
    private Logger() {}

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public static void log(String prefix, String message, Integer timestamp) {
        System.out.println("EVENT EXECUTED: " + "time: " + timestamp + ": " + prefix + ": " + message);
    }

    public static void log(String message, Integer timestamp) {
        System.out.println("EVENT EXECUTED: " + "time: " + timestamp + ": " + message);
    }
}
