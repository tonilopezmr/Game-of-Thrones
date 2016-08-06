package es.npatarino.android.gotchallenge.chat.message.data;

import android.support.annotation.NonNull;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.view.viewmodel.TextPayLoad;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessageRepository implements MessageDomain.Repository {

    private HashMap<Conversation, PublishSubject<Message>> messagesPublisher;
    private List<Message> messages;

    public MessageRepository() {
        messagesPublisher = new HashMap<>();
        messages = Arrays.asList(
                new Message("567", new User("asdf", "Arya Stark", "",
                        "http://www.bolsamania.com/seriesadictos/wp-content/uploads/2015/12/landscape-1436892099-arya-stark.jpg"),
                        1, false, new TextPayLoad("Joffrey\nCersei\nWalder Frey\nMeryn Trant\nTywin Lannister\n"
                        + "The red woman\nBeric Dondarrion\nThoros of myr\nIlyn payne\nThe mountain\nThe hound")),
                new Message("123", new User("asdf", "Daenerys Targaryen", "",
                        "http://winteriscoming.net/wp-content/uploads/2016/03/Daenerys-Targaryen-crop-630x371.jpg"),
                        3, false, new TextPayLoad("where my dwarf is?")),
                new Message("345", new User("asdf", "Tyrion Lannister", "",
                        "https://pbs.twimg.com/profile_images/668279339838935040/8sUE9d4C.jpg"),
                        2, false, new TextPayLoad("Tell me blonde who never burns")));
    }

    @NonNull
    private List<Message> getMessages() {
        List<Message> others = new ArrayList<>();

        for (Message message : messages) {
            others.add(new Message(message.getId()+"1", message.getUser(), message.getTimestamp()+3, message.isFromMe(), message.getPayload()));
        }
        return others;
    }

    @Override
    public Observable<List<Message>> getMessages(Conversation conversation) {
        Observable<List<Message>> messageObservable = Observable.just(new ArrayList<>(messages));

        return messageObservable;
    }

    @Override
    public Observable<Void> sendMessage(Message message, Conversation conversation) {
        getPublisher(conversation).onNext(message);
        return Observable.empty();
    }

    @Override
    public Observable<Message> subscribeToMessages(Conversation conversation) {
        List<Message> others = getMessages();


        Observable<Message> messageObservable  = Observable
                .interval(1, TimeUnit.SECONDS)
                .map(i -> others.get(i.intValue()))
                .take(others.size());

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
