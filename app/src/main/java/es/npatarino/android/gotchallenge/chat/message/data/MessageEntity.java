package es.npatarino.android.gotchallenge.chat.message.data;

import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;

public class MessageEntity {

    private String id;
    private User userFrom;
    private long timestamp;
    private boolean fromMe;
    private String message;

    public MessageEntity(String id, User userFrom, long timestamp, boolean fromMe, String message) {
        this.id = id;
        this.userFrom = userFrom;
        this.timestamp = timestamp;
        this.fromMe = fromMe;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public String getMessage() {
        return message;
    }
}
