package es.npatarino.android.gotchallenge.chat.message.data;

import es.npatarino.android.gotchallenge.chat.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.domain.model.User;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.chat.view.viewmodel.TextPayload;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MessageRepository implements MessageDomain.Repository {

    private HashMap<Conversation, PublishSubject<Message>> messagesPublisher;
    private List<Message> messages;

    public MessageRepository() {
        messagesPublisher = new HashMap<>();
        messages = Arrays.asList(
                new Message("1", new User("1", "Sergio",
                        "https://lh3.googleusercontent.com/-jDZYxFuC6X8/AAAAAAAAAAI/AAAAAAAAA-s/Wch5ttRQSQ0/s120-c/photo.jpg"),
                        234234234, false, new TextPayload("Hey buddy!")),
                new Message("2", new User("2", "Celia",
                        "https://lh3.googleusercontent.com/-wF2cRii1VJM/AAAAAAAAAAI/AAAAAAAAAEI/ouEFoadEehk/s120-c/photo.jpg"),
                        234234234, false, new TextPayload("Vamoh cohonee vente ya para malaga\nQue tengo una terraza bien hermosa como yo :D")),
                new Message("3", new User("3", "Cantero",
                        "https://lh3.googleusercontent.com/-8980rlwDIss/AAAAAAAAAAI/AAAAAAAAAAA/dRMUwVLajoc/s120-c/photo.jpg"),
                        234234234, false, new TextPayload("Vamonoh illoh")));
    }

    @Override
    public Observable<List<Message>> getMessages(Conversation conversation) {
        for (Message message : messages) {
            getPublisher(conversation).onNext(message);
        }
        return null;
    }

    @Override
    public Observable<Void> sendMessage(Message message, Conversation conversation) {
        getPublisher(conversation).onNext(message);
        return null;
    }

    @Override
    public Observable<Message> subscribeToMessages(Conversation conversation) {
        Observable<Message> messageObservable = Observable.create(subscriber -> {
            for (Message message : messages) {
                subscriber.onNext(message);
            }
            subscriber.onCompleted();
        });

        messageObservable.repeat(5);

        return getPublisher(conversation).mergeWith(messageObservable);
    }

    private PublishSubject<Message> getPublisher(Conversation conversation) {
        if (!messagesPublisher.containsKey(conversation)){
            PublishSubject<Message> publisher = PublishSubject.create();
            messagesPublisher.put(conversation, publisher);
        }

        return messagesPublisher.get(conversation);
    }
}
