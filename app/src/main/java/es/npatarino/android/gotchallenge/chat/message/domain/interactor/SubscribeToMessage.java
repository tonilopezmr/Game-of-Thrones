package es.npatarino.android.gotchallenge.chat.message.domain.interactor;

import es.npatarino.android.gotchallenge.base.detail.interactor.UseCase;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import rx.Observable;
import rx.Scheduler;

public class SubscribeToMessage extends UseCase<Message> {

    private MessageDomain.Repository repository;
    private Conversation conversation;

    public SubscribeToMessage(MessageDomain.Repository repository,
                                 Scheduler uiThread,
                                 Scheduler executorThread) {
        super(uiThread, executorThread);
        this.repository = repository;
    }

    public Observable<Message> execute(Conversation conversation){
        this.conversation = conversation;
        return execute();
    }

    @Override
    protected Observable<Message> buildUseCaseObservable() {
        return ScheduleOn(repository.subscribeToMessages(conversation));
    }
}
