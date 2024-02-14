package simulatorPkg;

public abstract class Node {
    protected Integer left;
    protected Integer right;
    protected Integer id;
    protected static Simulator simulator = Simulator.getInstance();

    //
    // Constructors
    //

    public Node(Integer left, Integer right, Integer id) {
        this.left = left;
        this.right = right;
        this.id = id;
    }

    public Node(Integer id) {
        this.id = id;
    }

    //
    // Event handeling
    //

    public abstract void handleEvent(Event event);

    //
    // Routing
    //




    public void setLeft(Integer left) {
        this.left = left;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getId() {
        return id;
    }
}
