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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (timestamp != message.timestamp) return false;
        if (fromMe != message.fromMe) return false;
        if (!id.equals(message.id)) return false;
        if (userFrom != null ? !userFrom.equals(message.userFrom) : message.userFrom != null) return false;
        return payload.equals(message.payload);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (userFrom != null ? userFrom.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (fromMe ? 1 : 0);
        result = 31 * result + payload.hashCode();
        return result;
    }
}
