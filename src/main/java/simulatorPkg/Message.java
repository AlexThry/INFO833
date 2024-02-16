package simulatorPkg;

public class Message {
    private String name;
    private Integer type;
    private Object content;

    public Message(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public Message(String name, Integer type, Object content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", content=" + content +
                '}';
    }
}
