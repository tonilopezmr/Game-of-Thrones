package es.npatarino.android.gotchallenge.chat.conversation.domain.model;

public class User {

    private String id;
    private String name;
    private String state;
    private String imageUrl;

    public User(String id, String name, String state, String imageUrl) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!name.equals(user.name)) return false;
        if (state != null ? !state.equals(user.state) : user.state != null) return false;
        return imageUrl != null ? imageUrl.equals(user.imageUrl) : user.imageUrl == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }
}
