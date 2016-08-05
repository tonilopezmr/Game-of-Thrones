package es.npatarino.android.gotchallenge.chat.message.data;

import es.npatarino.android.gotchallenge.chat.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.domain.model.User;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.chat.view.viewmodel.TextPayLoad;
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
                new Message("asdf", new User("asdf", "Daenerys Targaryen",
                        "http://winteriscoming.net/wp-content/uploads/2016/03/Daenerys-Targaryen-crop-630x371.jpg"),
                        234234234, false, new TextPayLoad("where my dwarf is?")),
                new Message("asdf", new User("asdf", "Tyrion Lannister",
                        "https://pbs.twimg.com/profile_images/668279339838935040/8sUE9d4C.jpg"),
                        234234234, false, new TextPayLoad("Tell me blonde who never burns")),
                new Message("asdf", new User("asdf", "Arya Stark",
                    "http://www.bolsamania.com/seriesadictos/wp-content/uploads/2015/12/landscape-1436892099-arya-stark.jpg"),
                    234234234, false, new TextPayLoad("Joffrey\nCersei\nWalder Frey\nMeryn Trant\nTywin Lannister\n"
                    + "The red woman\nBeric Dondarrion\nThoros of myr\nIlyn payne\nThe mountain\nThe hound")));
    }

    @Override
    public Observable<List<Message>> getMessages(Conversation conversation) {
        Observable<List<Message>> messageObservable = Observable.just(messages);

//        for (Message message : messages) {
//            getPublisher(conversation).onNext(message);
//        }
        return messageObservable;
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
