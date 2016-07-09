package es.npatarino.android.gotchallenge.chat.conversation.interactor;

import es.npatarino.android.gotchallenge.base.interactor.UseCase;
import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.domain.model.Conversation;
import rx.Observable;
import rx.Scheduler;

public class CreateOrUpdateConversation extends UseCase<Void> {

    private ConversationDomain.Repository repository;
    private Conversation conversation;

    protected CreateOrUpdateConversation(ConversationDomain.Repository repository,
                                         Scheduler uiThread,
                                         Scheduler executorThread) {
        super(uiThread, executorThread);
        this.repository = repository;
    }

    public Observable<Void> execute(Conversation conversation){
        this.conversation = conversation;
        return execute();
    }

    @Override
    protected Observable<Void> buildUseCaseObservable() {
        if (conversation == null) throw new IllegalStateException("Must set conversation to create or update");

        return repository.createOrUpdate(conversation);
    }
}
