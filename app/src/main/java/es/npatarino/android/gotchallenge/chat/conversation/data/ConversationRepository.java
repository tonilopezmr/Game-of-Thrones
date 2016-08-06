package es.npatarino.android.gotchallenge.chat.conversation.data;

import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;
import rx.Observable;

import java.util.Arrays;

public class ConversationRepository implements ConversationDomain.Repository {

    @Override
    public Observable<Conversation> get(Conversation conversation) {
        return Observable.just(new Conversation("1",
                "Stark",
                Arrays.asList(new User("", "Pepe", "", ""),
                        new User("", "Luis", "", ""),
                        new User("", "Pepa", "", ""),
                        new User("", "Marco", "", "")),
                null,
                "https://s3-eu-west-1.amazonaws.com/npatarino/got/stark.jpg"));
    }

}
