package es.npatarino.android.gotchallenge;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import es.npatarino.android.gotchallenge.di.AppComponent;

/**
 * @author Antonio López.
 */
public class GotChallengeApplication extends Application{
    private AppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();
//        appComponent = DaggerAppComponent.builder()
//                .appModule(new AppModule())
//                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @VisibleForTesting
    public void setComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}
