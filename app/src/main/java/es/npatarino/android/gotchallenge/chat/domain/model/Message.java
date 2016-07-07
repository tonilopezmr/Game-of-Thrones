package es.npatarino.android.gotchallenge.chat.domain.model;

public class Message {

    private String id;
    private User userFrom;
    private long timestamp;
    private boolean fromMe;
    private Payload payload;


    public Message(String id,
                   User userFrom,
                   long timestamp,
                   boolean fromMe,
                   Payload payload) {
        this.id = id;
        this.userFrom = userFrom;
        this.timestamp = timestamp;
        this.fromMe = fromMe;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return userFrom;
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
