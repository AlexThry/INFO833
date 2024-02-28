package simulator;

import dht.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorTest {

    Simulator simulator = Simulator.getInstance();

    Message joinRequestMessage = new Message(Message.JOIN_REQUEST);

    Event join1 = new Event(61, 61, 34, joinRequestMessage, 1);
    Event join2 = new Event(26, 26, 50, joinRequestMessage, 1);
    Event join3 = new Event(59, 59, 34, joinRequestMessage, 6);

    @BeforeEach
    void setUp() {
        simulator.addEvent(join1);
        simulator.addEvent(join2);
        simulator.addEvent(join3);
    }

    @Test
    void getInstance() {
        Simulator instance1 = Simulator.getInstance();
        Simulator instance2 = Simulator.getInstance();

        assertNotNull(instance1);

        assertSame(instance1, instance2);
    }

    @Test
    void removeEvent() {
        int initialSize = Simulator.getEventList().size();
        Simulator.removeEvent();
        int finalSize = Simulator.getEventList().size();

        assertEquals(initialSize - 1, finalSize);


    }

    @Test
    void addEvent() {
        int initialSize = Simulator.getEventList().size();
        Event newEvent = new Event(59, 59, 34, joinRequestMessage, 6);
        int finalSize = Simulator.getEventList().size();

        assertEquals(initialSize, finalSize);
    }
}