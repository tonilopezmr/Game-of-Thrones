package es.npatarino.android.gotchallenge;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import es.npatarino.android.gotchallenge.characters.di.CharacterComponent;
import es.npatarino.android.gotchallenge.characters.di.CharacterModule;
import es.npatarino.android.gotchallenge.chat.conversation.di.ConversationComponent;
import es.npatarino.android.gotchallenge.chat.conversation.di.ConversationModule;
import es.npatarino.android.gotchallenge.chat.message.di.MessageComponent;
import es.npatarino.android.gotchallenge.chat.message.di.MessageModule;
import es.npatarino.android.gotchallenge.common.di.application.AppComponent;
import es.npatarino.android.gotchallenge.common.di.application.AppModule;
import es.npatarino.android.gotchallenge.common.di.application.DaggerAppComponent;
import es.npatarino.android.gotchallenge.houses.di.HouseComponent;
import es.npatarino.android.gotchallenge.houses.di.HouseModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class GotChallengeApplication extends Application {

    private AppComponent appComponent;
    private CharacterComponent characterComponent;
    private ConversationComponent conversationComponent;
    private MessageComponent messageComponent;
    private HouseComponent houseComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();

        initializeRealmConfiguration();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @VisibleForTesting
    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    public CharacterComponent getCharacterComponent() {
        if (characterComponent == null) {
            characterComponent = appComponent.plus(new CharacterModule());
        }

        return characterComponent;
    }

    public ConversationComponent getConversationComponent() {
        if (conversationComponent == null) {
            conversationComponent = appComponent.plus(new ConversationModule());
        }

        return conversationComponent;
    }

    public MessageComponent getMessageComponent() {
        if (messageComponent == null) {
            messageComponent = appComponent.plus(new MessageModule());
        }

        return messageComponent;
    }

    public HouseComponent getHouseComponent() {
        if (houseComponent == null) {
            houseComponent = appComponent.plus(new HouseModule());
        }

        return houseComponent;
    }

    public void releaseMessageComponent() {
        messageComponent = null;
    }

    public void releaseConversationComponent() {
        conversationComponent = null;
    }

    public void releaseHouseComponent() {
        houseComponent = null;
    }

    public void releaseCharacterComponent() {
        characterComponent = null;
    }

    public static GotChallengeApplication get(Context context) {
        return (GotChallengeApplication) context.getApplicationContext();
    }

    private void initializeRealmConfiguration() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext())
                .name("com.tonilopezmr.got.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
