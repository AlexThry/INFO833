package simulatorPkg;

public abstract class Node {
    protected Integer left;
    protected Integer right;
    protected Integer DHTid;
    protected Integer IP;
    protected static Simulator simulator = Simulator.getInstance();
    protected static Network network = Network.getInstance();

    //
    // Constructors
    //

    public Node(Integer left, Integer right, Integer DHTid, Integer IP) {
        this.left = left;
        this.right = right;
        this.DHTid = DHTid;
        this.IP = IP;
    }

    public Node(Integer DHTid, Integer IP) {
        this.DHTid = DHTid;
        this.IP = IP;
    }

    //
    // Event handeling
    //

    public abstract void handleEvent(Event event);

    //
    // Routing
    //

    public abstract void route(Event event);


    public void setLeft(Integer left) {
        this.left = left;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getDHTid() {
        return DHTid;
    }

    public Integer getIP() {
        return IP;
    }

    public static Simulator getSimulator() {
        return simulator;
    }

    public static Network getNetwork() {
        return network;
    }

    @Override
    public String toString() {
        return "Node{" +
                "left=" + left +
                ", right=" + right +
                ", DHTid=" + DHTid +
                ", IP=" + IP +
                '}';
    }
}
