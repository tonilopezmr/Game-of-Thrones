package es.npatarino.android.gotchallenge.chat.conversation.domain.interactor;

import es.npatarino.android.gotchallenge.base.interactor.UseCase;
import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;
import rx.Observable;
import rx.Scheduler;

public class AddUser extends UseCase<Void> {

    private ConversationDomain.Repository repository;
    private User user;
    private Conversation conversation;

    protected AddUser(ConversationDomain.Repository repository,
                                 Scheduler uiThread,
                                 Scheduler executorThread) {
        super(uiThread, executorThread);
        this.repository = repository;
    }

    public Observable<Void> execute(User user, Conversation conversation){
        this.user = user;
        this.conversation = conversation;
        return execute();
    }

    @Override
    protected Observable<Void> buildUseCaseObservable() {
        if (user == null || conversation == null)
            throw new IllegalStateException("Must set user and conversation to add user to conversation");
        return repository.add(user, conversation);
    }
}
