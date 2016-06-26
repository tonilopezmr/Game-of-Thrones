package es.npatarino.android.gotchallenge.houses.data.source.local.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BddHouse extends RealmObject {

    public static final String PRIMARY_KEY_NAME = "houseId";

    @PrimaryKey
    private String houseId;
    private String houseName;
    private String houseImageUrl;

    public BddHouse() {
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseImageUrl() {
        return houseImageUrl;
    }

    public void setHouseImageUrl(String houseImageUrl) {
        this.houseImageUrl = houseImageUrl;
    }
}
