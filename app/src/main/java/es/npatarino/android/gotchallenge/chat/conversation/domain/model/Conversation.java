package es.npatarino.android.gotchallenge.chat.conversation.domain.model;

import es.npatarino.android.gotchallenge.chat.message.domain.model.Payload;

import java.util.List;

public class Conversation {

    private String id;
    private String name;
    private List<User> users;
    private Payload lastMessage;
    private String imageUrl;

    public Conversation(String id, String name, List<User> users, Payload lastMessage, String imageUrl) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.lastMessage = lastMessage;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
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
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        if (lastMessage != null ? !lastMessage.equals(that.lastMessage) : that.lastMessage != null) return false;
        return imageUrl != null ? imageUrl.equals(that.imageUrl) : that.imageUrl == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (lastMessage != null ? lastMessage.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }
}
