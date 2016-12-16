package es.npatarino.android.gotchallenge.chat.message;

import es.npatarino.android.gotchallenge.chat.conversation.model.Conversation;
import es.npatarino.android.gotchallenge.chat.message.model.Message;
import rx.Observable;

import java.util.List;

public interface MessageDomain {

  interface Repository {
    Observable<List<Message>> getMessages(Conversation conversation);

    //TODO: Observable<Message> getLastMessage(Conversation conversation);
    Observable<Void> sendMessage(Message message, Conversation conversation);

    Observable<Message> subscribeToMessages(Conversation conversation);
  }

}
