package es.npatarino.android.gotchallenge.chat.ui;

import android.support.test.InstrumentationRegistry;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.common.di.application.AppComponent;
import es.npatarino.android.gotchallenge.common.di.application.AppModule;
import it.cosenonjaviste.daggermock.DaggerMockRule;

public class ChatActivityDaggerMockRule extends DaggerMockRule<AppComponent> {

    private MockMessageRepository mockMessageRepository;

    public ChatActivityDaggerMockRule() {
        super(AppComponent.class, new AppModule(getApp()));
        this.mockMessageRepository = new MockMessageRepository(getApp());
        provides(MessageDomain.Repository.class, mockMessageRepository);
        set(component -> getApp().setAppComponent(component));
    }

    public MockMessageRepository getMockMessageRepository() {
        return mockMessageRepository;
    }

    private static GotChallengeApplication getApp() {
        return (GotChallengeApplication) InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext()
                .getApplicationContext();
    }
}
