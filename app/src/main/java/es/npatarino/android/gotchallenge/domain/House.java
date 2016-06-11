package es.npatarino.android.gotchallenge.domain;

public class House {

    private String houseImageUrl;
    private String houseName;
    private String houseId;

    public House() {
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

        House house = (House) o;

        return houseId != null ? houseId.equals(house.houseId) : house.houseId == null;
    }

    @Override
    public int hashCode() {
        return houseId.hashCode();
    }
}