package es.npatarino.android.gotchallenge.chat.conversation.data;

import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import rx.Observable;

public class ConversationRepository implements ConversationDomain.Repository {

    @Override
    public Observable<Conversation> get(Conversation conversation) {
        return null;
    }

}
