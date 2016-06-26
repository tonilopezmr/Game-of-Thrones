package es.npatarino.android.gotchallenge.characters.data.source.local.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BddGoTCharacter extends RealmObject {

    public static final String PRIMARY_KEY_NAME = "name";

    @PrimaryKey
    private String name;
    private String imageUrl;
    private String description;
    private String houseId;

    public BddGoTCharacter() {
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

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
