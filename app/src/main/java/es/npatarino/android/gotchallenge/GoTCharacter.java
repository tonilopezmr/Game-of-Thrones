package es.npatarino.android.gotchallenge;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicolás Patarino on 21/02/16.
 */
public class GoTCharacter {

    @SerializedName("name")
    String n;
    @SerializedName("imageUrl")
    String iu;
    @SerializedName("description")
    String d;
    @SerializedName("houseImageUrl")
    String hu;
    @SerializedName("houseName")
    String hn;
    @SerializedName("houseId")
    String hi;

    public String getHu() {
        return hu;
    }

    public void setHu(final String s) {
        this.hu = s;
    }

    public String getHn() {
        return hn;
    }

    public void setHn(final String s) {
        this.hn = s;
    }

    public String getHi() {
        return hi;
    }

    public void setHi(final String s) {
        this.hi = s;
    }

    public String getN() {
        return n;
    }

    public void setN(final String s) {
        this.n = s;
    }

    public String getIu() {
        return iu;
    }

    public void setIu(final String s) {
        this.iu = s;
    }

    public String getD() {
        return d;
    }

    public void setD(final String s) {
        this.d = s;
    }

    /**
     * Created by Nicolás Patarino on 21/02/16.
     */
    public static class GoTHouse {

        @SerializedName("houseImageUrl")
        String u;
        @SerializedName("houseName")
        String n;
        @SerializedName("houseId")
        String i;

        public String getU() {
            return u;
        }

        public void setU(final String houseImageUrl) {
            this.u = houseImageUrl;
        }

        public String getN() {
            return n;
        }

        public void setN(final String houseName) {
            this.n = houseName;
        }

        public String getI() {
            return i;
        }

        public void setI(final String houseId) {
            this.i = houseId;
        }

    }
}
