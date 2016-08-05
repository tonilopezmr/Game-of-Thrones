package es.npatarino.android.gotchallenge.chat.message.domain.interactor;

import es.npatarino.android.gotchallenge.base.detail.interactor.UseCase;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import rx.Observable;
import rx.Scheduler;

public class SendMessage extends UseCase<Void> {

    private Conversation conversation;
    private Message message;
    private MessageDomain.Repository repository;

    public SendMessage(MessageDomain.Repository repository,
                       Scheduler uiThread,
                       Scheduler executorThread) {
        super(uiThread, executorThread);
        this.repository = repository;
    }

    public Observable<Void> execute(Message message, Conversation conversation){
        this.message = message;
        this.conversation = conversation;
        return execute();
    }

    @Override
    protected Observable<Void> buildUseCaseObservable() {
        if (message == null || conversation == null)
            throw new IllegalStateException("Must set message and conversation to send a message");
        return ScheduleOn(repository.sendMessage(message, conversation));
    }
}
