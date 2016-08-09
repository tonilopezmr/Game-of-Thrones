package es.npatarino.android.gotchallenge.chat.conversation.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.conversation.data.ConversationRepository;
import es.npatarino.android.gotchallenge.chat.conversation.domain.interactor.GetConversation;
import es.npatarino.android.gotchallenge.chat.di.ChatScope;
import es.npatarino.android.gotchallenge.common.di.ExecutorThread;
import es.npatarino.android.gotchallenge.common.di.UiThread;
import rx.Scheduler;

@Module
public class ConversationModule {

    @Provides
    @ChatScope
    public ConversationDomain.Repository getConversationRepository(Context context) {
        return new ConversationRepository(context);
    }

    @Provides
    @ChatScope
    public GetConversation getConversation(ConversationDomain.Repository repository,
                                           @UiThread Scheduler ui,
                                           @ExecutorThread Scheduler executor) {
        return new GetConversation(repository, ui, executor);
    }
    
}
