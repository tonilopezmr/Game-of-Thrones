package es.npatarino.android.gotchallenge.chat.message.interactor;

import es.npatarino.android.gotchallenge.base.interactor.UseCase;
import es.npatarino.android.gotchallenge.chat.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import rx.Observable;
import rx.Scheduler;

public class SendMessage extends UseCase<Void> {

    private Conversation conversation;
    private Message message;
    private MessageDomain.Repository repository;

    protected SendMessage(MessageDomain.Repository repository,
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
        return repository.sendMessage(message, conversation);
    }
}
