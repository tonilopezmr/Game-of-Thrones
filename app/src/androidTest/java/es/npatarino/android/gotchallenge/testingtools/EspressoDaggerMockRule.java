package es.npatarino.android.gotchallenge.testingtools;

import android.support.test.InstrumentationRegistry;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.common.di.application.AppComponent;
import es.npatarino.android.gotchallenge.common.di.application.AppModule;
import it.cosenonjaviste.daggermock.DaggerMockRule;

public class EspressoDaggerMockRule extends DaggerMockRule<AppComponent> {

    public EspressoDaggerMockRule() {
        super(AppComponent.class, new AppModule(getApp().getApplicationContext()));
        set(component -> getApp().setAppComponent(component));
    }

    public static GotChallengeApplication getApp() {
        return (GotChallengeApplication) InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext()
                .getApplicationContext();
    }
}

