package net.intelie.challenge.domain.models;

/**
 * This is just an event stub, feel free to expand it if needed.
 */

public class Event {
    private final String type;
    private final long timestamp;

    public Event(String type, long timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }

    public String type() {
        return type;
    }

    public long timestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Event {" +
                " type = '" + type + '\'' +
                ", timestamp = " + timestamp +
                " }";
    }
}
