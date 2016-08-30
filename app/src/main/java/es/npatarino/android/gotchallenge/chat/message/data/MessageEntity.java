package es.npatarino.android.gotchallenge.chat.message.data;

import es.npatarino.android.gotchallenge.chat.conversation.model.User;

public class MessageEntity {

    private String id;
    private User userFrom;
    private long timestamp;
    private boolean fromMe;
    private String message;
    private String messageImageUrl;
    private String sticker;

    public MessageEntity(String id,
                         User userFrom,
                         long timestamp,
                         boolean fromMe,
                         String message,
                         String messageImageUrl,
                         String sticker) {
        this.id = id;
        this.userFrom = userFrom;
        this.timestamp = timestamp;
        this.fromMe = fromMe;
        this.message = message;
        this.messageImageUrl = messageImageUrl;
        this.sticker = sticker;
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

    public String getImageUrl() {
        return messageImageUrl;
    }

    public String getSticker() {
        return sticker;
    }
}
