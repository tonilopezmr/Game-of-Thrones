package es.npatarino.android.gotchallenge.data.caching;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TimeProvider {

    private static final String KEY = "persisted_time_key";
    private Context context;

    public TimeProvider(Context context) {
        this.context = context;
    }

    public void persistTime(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit()
                .putLong(KEY, System.currentTimeMillis())
                .apply();
    }

    public long getPersistedTime(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(KEY, System.currentTimeMillis());
    }
}
