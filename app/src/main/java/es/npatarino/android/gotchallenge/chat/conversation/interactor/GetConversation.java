package es.npatarino.android.gotchallenge.chat.conversation.interactor;

import es.npatarino.android.gotchallenge.base.interactor.UseCase;
import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.domain.model.Conversation;
import rx.Observable;
import rx.Scheduler;

public class GetConversation extends UseCase<Conversation> {

    private ConversationDomain.Repository repository;
    private Conversation conversation;

    protected GetConversation(ConversationDomain.Repository repository,
                                 Scheduler uiThread,
                                 Scheduler executorThread) {
        super(uiThread, executorThread);
        this.repository = repository;
    }

    public Observable<Conversation> execute(Conversation conversation) {
        this.conversation = conversation;
        return execute();
    }

    @Override
    protected Observable<Conversation> buildUseCaseObservable() {
        if (conversation == null) throw new IllegalStateException("Must set conversation to get");

        return repository.get(conversation);
    }

}
