package es.npatarino.android.gotchallenge.chat.domain.model;

public class User {

    private String id;
    private String name;
    private String imageUrl;

    public User(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
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
}
