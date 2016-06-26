package es.npatarino.android.gotchallenge.characters.domain.model;

public class GoTCharacter {

    private String name;
    private String imageUrl;
    private String description;
    private String houseImageUrl;
    private String houseName;
    private String houseId;

    public GoTCharacter() {
    }

    public GoTCharacter(String name, String imageUrl, String description, String houseImageUrl, String houseName, String houseId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.houseImageUrl = houseImageUrl;
        this.houseName = houseName;
        this.houseId = houseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHouseImageUrl() {
        return houseImageUrl;
    }

    public void setHouseImageUrl(String houseImageUrl) {
        this.houseImageUrl = houseImageUrl;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoTCharacter character = (GoTCharacter) o;

        return name != null ? name.equals(character.name) : character.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
