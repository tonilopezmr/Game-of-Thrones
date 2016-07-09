package es.npatarino.android.gotchallenge.chat.conversation.data;

import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.domain.model.User;
import rx.Observable;

public class ConversationRepository implements ConversationDomain.Repository {

    @Override
    public Observable<Conversation> get(Conversation conversation) {
        return null;
    }

    @Override
    public Observable<Void> createOrUpdate(Conversation conversation) {
        return null;
    }

    @Override
    public Observable<Void> add(User user, Conversation conversation) {
        return null;
    }

}
