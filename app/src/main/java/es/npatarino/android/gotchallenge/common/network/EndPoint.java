package es.npatarino.android.gotchallenge.common.network;

public class EndPoint {

    private String endPoint;

    public EndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return endPoint;
    }
}
