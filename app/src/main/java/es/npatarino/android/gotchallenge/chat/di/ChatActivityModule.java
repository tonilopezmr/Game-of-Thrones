package es.npatarino.android.gotchallenge.chat.di;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.chat.conversation.usecases.GetConversation;
import es.npatarino.android.gotchallenge.chat.conversation.presenter.ConversationPresenter;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityModule;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityScope;

@Module
public class ChatActivityModule extends ActivityModule {

    @Provides
    @ActivityScope
    public ConversationPresenter conversationPresenter(GetConversation getConversation) {
        return new ConversationPresenter(getConversation);
    }
}
