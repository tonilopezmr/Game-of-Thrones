package es.npatarino.android.gotchallenge.data.caching;

public class TimeProvider {
    public long getPersistedTime(){
        return System.currentTimeMillis();
    }
}
