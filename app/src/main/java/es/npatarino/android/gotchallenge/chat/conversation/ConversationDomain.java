package es.npatarino.android.gotchallenge.chat.conversation;

import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import rx.Observable;

public interface ConversationDomain {

    interface Repository {
        Observable<Conversation> get(Conversation conversation);
//        Observable<Void> createOrUpdate(Conversation conversation);
//        Observable<Void> add(User user, Conversation conversation);
    }

}
