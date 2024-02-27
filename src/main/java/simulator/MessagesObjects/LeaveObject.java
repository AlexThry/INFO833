package simulator.MessagesObjects;

import simulator.Message;
import simulator.MessageObject;

public class LeaveObject implements MessageObject {
    private Integer left;
    private Integer right;

    public LeaveObject(Integer left, Integer right) {
        this.left = left;
        this.right = right;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getRight() {
        return right;
    }
}
