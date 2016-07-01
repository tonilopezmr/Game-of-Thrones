package es.npatarino.android.gotchallenge;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import es.npatarino.android.gotchallenge.base.di.components.AppComponent;
import es.npatarino.android.gotchallenge.base.di.components.DaggerAppComponent;
import es.npatarino.android.gotchallenge.base.di.modules.AppModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class GotChallengeApplication extends Application{
    private AppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
        initializeRealmConfiguration();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @VisibleForTesting
    public void setComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    private void initializeRealmConfiguration() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext())
                .name("com.tonilopezmr.got.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
