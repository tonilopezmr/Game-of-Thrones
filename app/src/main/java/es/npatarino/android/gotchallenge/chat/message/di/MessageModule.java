package es.npatarino.android.gotchallenge.chat.message.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.chat.di.ChatScope;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.chat.message.data.MessageRepository;
import es.npatarino.android.gotchallenge.chat.message.usecases.GetMessages;
import es.npatarino.android.gotchallenge.chat.message.usecases.SendMessage;
import es.npatarino.android.gotchallenge.chat.message.usecases.SubscribeToMessage;
import es.npatarino.android.gotchallenge.common.di.ExecutorThread;
import es.npatarino.android.gotchallenge.common.di.UiThread;
import rx.Scheduler;

@Module
public class MessageModule {

    @Provides
    @ChatScope
    public MessageDomain.Repository messageRepository(Context context) {
        return new MessageRepository(context);
    }

    @Provides
    @ChatScope
    public SubscribeToMessage subscribeToMessage(MessageDomain.Repository messageRepository,
                                                 @UiThread Scheduler ui,
                                                 @ExecutorThread Scheduler executor) {
        return new SubscribeToMessage(messageRepository, ui, executor);
    }

    @Provides
    @ChatScope
    public SendMessage sendMessage(MessageDomain.Repository messageRepository,
                                   @UiThread Scheduler ui,
                                   @ExecutorThread Scheduler executor) {
        return new SendMessage(messageRepository, ui, executor);
    }

    @Provides
    @ChatScope
    public GetMessages getMessage(MessageDomain.Repository messageRepository,
                                  @UiThread Scheduler ui,
                                  @ExecutorThread Scheduler executor) {
        return new GetMessages(messageRepository, ui, executor);
    }

}
