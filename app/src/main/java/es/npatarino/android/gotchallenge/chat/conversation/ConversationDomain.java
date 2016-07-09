package es.npatarino.android.gotchallenge.chat.conversation;

import es.npatarino.android.gotchallenge.chat.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.domain.model.User;
import rx.Observable;

public interface ConversationDomain {

    interface Repository {
        Observable<Conversation> get(Conversation conversation);
        Observable<Void> createOrUpdate(Conversation conversation);
        Observable<Void> add(User user, Conversation conversation);
    }

}
