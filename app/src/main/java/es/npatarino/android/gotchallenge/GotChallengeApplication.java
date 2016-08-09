package es.npatarino.android.gotchallenge;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import es.npatarino.android.gotchallenge.characters.di.CharacterComponent;
import es.npatarino.android.gotchallenge.characters.di.CharacterModule;
import es.npatarino.android.gotchallenge.common.di.application.AppComponent;
import es.npatarino.android.gotchallenge.common.di.application.AppModule;
import es.npatarino.android.gotchallenge.common.di.application.DaggerAppComponent;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class GotChallengeApplication extends Application {

    private AppComponent appComponent;
    private CharacterComponent characterComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
        initializeRealmConfiguration();
    }

    public static GotChallengeApplication get(Context context){
        return (GotChallengeApplication) context.getApplicationContext();
    }

    public CharacterComponent createCharacterComponent(){
        characterComponent = appComponent.plus(new CharacterModule());
        return characterComponent;
    }

    public CharacterComponent getCharacterComponent() {
        return characterComponent;
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
