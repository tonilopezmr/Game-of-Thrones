package es.npatarino.android.gotchallenge.houses.domain.model;

public class GoTHouse {

    private String id;
    private String name;
    private String imageUrl;

    public GoTHouse(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoTHouse goTHouse = (GoTHouse) o;

        if (!id.equals(goTHouse.id)) return false;
        if (!name.equals(goTHouse.name)) return false;
        return imageUrl != null ? imageUrl.equals(goTHouse.imageUrl) : goTHouse.imageUrl == null;

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}