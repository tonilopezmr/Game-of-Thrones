package es.npatarino.android.gotchallenge.chat.di;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.GetMessages;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SendMessage;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SubscribeToMessage;
import es.npatarino.android.gotchallenge.chat.message.presenter.MessagePresenter;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityModule;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityScope;

@Module
public class ChatFragmentModule extends ActivityModule {

    @Provides
    @ActivityScope
    public MessagePresenter messagePresenter(SubscribeToMessage subscribeToMessage,
                                             GetMessages getMessages,
                                             SendMessage sendMessage) {
        return new MessagePresenter(subscribeToMessage, getMessages, sendMessage);
    }
}
