package es.npatarino.android.gotchallenge.domain;

/**
 * @author Antonio López.
 */
/**
 * Created by Nicolás Patarino on 21/02/16.
 */
public class GoTHouse {

    private String houseImageUrl;
    private String houseName;
    private String houseId;

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
}