package simulator.MessagesObjects;

import simulator.MessageObject;

public class SendObject implements MessageObject {
    private Object message;

    public SendObject(Object message) {
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }
}
