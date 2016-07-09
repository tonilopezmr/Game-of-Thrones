package es.npatarino.android.gotchallenge.chat.domain.model;

import java.util.List;

public class Conversation {

    private String id;
    private String name;
    private List<User> participants;
    private Payload lastMessage;
    private String imageUrl;

    public Conversation(String id, String name, List<User> participants, Payload lastMessage, String imageUrl) {
        this.id = id;
        this.name = name;
        this.participants = participants;
        this.lastMessage = lastMessage;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public Payload getLastMessage() {
        return lastMessage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conversation that = (Conversation) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (participants != null ? !participants.equals(that.participants) : that.participants != null) return false;
        if (lastMessage != null ? !lastMessage.equals(that.lastMessage) : that.lastMessage != null) return false;
        return imageUrl != null ? imageUrl.equals(that.imageUrl) : that.imageUrl == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (participants != null ? participants.hashCode() : 0);
        result = 31 * result + (lastMessage != null ? lastMessage.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }
}
