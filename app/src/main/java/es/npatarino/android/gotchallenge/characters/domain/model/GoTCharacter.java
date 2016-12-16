package es.npatarino.android.gotchallenge.characters.domain.model;

import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;

public class GoTCharacter {

    private String name;
    private String imageUrl;
    private String description;
    private GoTHouse house;

    public GoTCharacter(String name,
                        String imageUrl,
                        String description,
                        GoTHouse house) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.house = house;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public GoTHouse getHouse() {
        return house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoTCharacter that = (GoTCharacter) o;

        if (!name.equals(that.name)) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return house != null ? house.equals(that.house) : that.house == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
