package es.npatarino.android.gotchallenge.chat.domain.model;

public class Message {

    private String id;
    private String message;
    private long timestamp;
    private boolean fromMe;
    private Payload payload;


    public Message(String id, String message, long timestamp, boolean fromMe, Payload payload) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.fromMe = fromMe;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
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
