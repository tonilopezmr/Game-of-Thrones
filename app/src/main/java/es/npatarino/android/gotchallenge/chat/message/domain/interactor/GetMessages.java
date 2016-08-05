package es.npatarino.android.gotchallenge.chat.message.domain.interactor;

import es.npatarino.android.gotchallenge.base.interactor.UseCase;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import rx.Observable;
import rx.Scheduler;

import java.util.List;

public class GetMessages extends UseCase<List<Message>> {

    private MessageDomain.Repository repository;
    private Conversation conversation;

    public GetMessages(MessageDomain.Repository repository,
                          Scheduler uiThread,
                          Scheduler executorThread) {
        super(uiThread, executorThread);
        this.repository = repository;
    }

    public Observable<List<Message>> execute(Conversation conversation){
        this.conversation = conversation;
        return execute();
    }

    @Override
    protected Observable<List<Message>> buildUseCaseObservable() {
        return ScheduleOn(repository.getMessages(conversation));
    }
}
