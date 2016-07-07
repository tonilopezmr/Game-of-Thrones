package es.npatarino.android.gotchallenge.chat.domain.model;

public class Message {

    private String id;
    private String from;
    private long timestamp;
    private boolean fromMe;
    private Payload payload;


    public Message(String id, String from, long timestamp, boolean fromMe, Payload payload) {
        this.id = id;
        this.from = from;
        this.timestamp = timestamp;
        this.fromMe = fromMe;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public Payload getPayload() {
        return payload;
    }
}
