package es.npatarino.android.gotchallenge.chat.di;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.base.di.ActivityScope;
import es.npatarino.android.gotchallenge.base.di.ExecutorThread;
import es.npatarino.android.gotchallenge.base.di.UiThread;
import es.npatarino.android.gotchallenge.chat.message.data.MessageRepository;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SubscribeToMessage;
import rx.Scheduler;

@Module
public class ChatModule {


    @Provides
    @ActivityScope
    public MessageRepository messageRepository(){
        return new MessageRepository();
    }

    @Provides
    @ActivityScope
    public SubscribeToMessage subscribeToMessage(MessageRepository messageRepository,
                                                 @UiThread Scheduler ui,
                                                 @ExecutorThread Scheduler executor){
        return new SubscribeToMessage(messageRepository, ui, executor);
    }

}
