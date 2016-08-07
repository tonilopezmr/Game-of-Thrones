package es.npatarino.android.gotchallenge.chat.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.base.di.ActivityScope;
import es.npatarino.android.gotchallenge.base.di.ExecutorThread;
import es.npatarino.android.gotchallenge.base.di.UiThread;
import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.conversation.data.ConversationRepository;
import es.npatarino.android.gotchallenge.chat.conversation.domain.interactor.GetConversation;
import es.npatarino.android.gotchallenge.chat.conversation.presenter.ConversationPresenter;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.chat.message.data.MessageRepository;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.GetMessages;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SendMessage;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SubscribeToMessage;
import es.npatarino.android.gotchallenge.chat.message.presenter.MessagePresenter;
import rx.Scheduler;

@Module
public class ChatModule {


    @Provides
    @ActivityScope
    public MessageDomain.Repository messageRepository() {
        return new MessageRepository();
    }

    @Provides
    @ActivityScope
    public SubscribeToMessage subscribeToMessage(MessageDomain.Repository messageRepository,
                                                 @UiThread Scheduler ui,
                                                 @ExecutorThread Scheduler executor) {
        return new SubscribeToMessage(messageRepository, ui, executor);
    }

    @Provides
    @ActivityScope
    public SendMessage sendMessage(MessageDomain.Repository messageRepository,
                                   @UiThread Scheduler ui,
                                   @ExecutorThread Scheduler executor) {
        return new SendMessage(messageRepository, ui, executor);
    }

    @Provides
    @ActivityScope
    public GetMessages getMessage(MessageDomain.Repository messageRepository,
                                  @UiThread Scheduler ui,
                                  @ExecutorThread Scheduler executor) {
        return new GetMessages(messageRepository, ui, executor);
    }


    @Provides
    @ActivityScope
    public MessagePresenter messagePresenter(SubscribeToMessage subscribeToMessage,
                                             GetMessages getMessages,
                                             SendMessage sendMessage) {
        return new MessagePresenter(subscribeToMessage, getMessages, sendMessage);
    }

    @Provides
    @ActivityScope
    public ConversationDomain.Repository getConversationRepository(Context context){
        return new ConversationRepository(context);
    }

    @Provides
    @ActivityScope
    public GetConversation getConversation(ConversationDomain.Repository repository,
                                           @UiThread Scheduler ui,
                                           @ExecutorThread Scheduler executor) {
        return new GetConversation(repository, ui, executor);
    }

    @Provides
    @ActivityScope
    public ConversationPresenter conversationPresenter(GetConversation getConversation) {
        return new ConversationPresenter(getConversation);
    }
}
