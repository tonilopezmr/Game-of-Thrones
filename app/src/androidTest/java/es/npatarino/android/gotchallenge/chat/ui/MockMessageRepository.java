package es.npatarino.android.gotchallenge.chat.ui;

import android.content.Context;
import es.npatarino.android.gotchallenge.chat.conversation.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.model.User;
import es.npatarino.android.gotchallenge.chat.message.data.MessageRepository;
import es.npatarino.android.gotchallenge.chat.message.model.Message;
import es.npatarino.android.gotchallenge.chat.message.viewmodel.TextPayLoad;
import rx.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MockMessageRepository extends MessageRepository {

  public boolean disableMessages;

  public MockMessageRepository(Context context) {
    super(context);
  }

  public void enableMessages() {
    this.disableMessages = false;
  }

  public void disableMessages() {
    this.disableMessages = true;
  }

  @Override
  public Observable<List<Message>> getMessages(Conversation conversation) {
    return getMessagesObservable();
  }

  private Observable<List<Message>> getMessagesObservable() {
    return Observable.fromCallable(this::getMessages);
  }

  private List<Message> getMessages() {
    List<Message> messages = Arrays.asList(
        new Message("1", new User("1", "Arya Stark", "",
            "http://www.bolsamania.com/seriesadictos/wp-content/uploads/2015/12/landscape-1436892099-arya-stark.jpg"),
            1, false, new TextPayLoad("Joffrey\nCersei\nWalder Frey\nMeryn Trant\nTywin Lannister\n"
            + "The red woman\nBeric Dondarrion\nThoros of myr\nIlyn payne\nThe mountain\nThe hound")),
        new Message("2", new User("2", "Daenerys Targaryen", "",
            "http://winteriscoming.net/wp-content/uploads/2016/03/Daenerys-Targaryen-crop-630x371.jpg"),
            5, false, new TextPayLoad("where my dwarf is?")),
        new Message("3", new User("3", "Tyrion Lannister", "",
            "https://pbs.twimg.com/profile_images/668279339838935040/8sUE9d4C.jpg"),
            3, false, new TextPayLoad("Tell me blonde who never burns")));

    return new ArrayList<>(disableMessages ? Collections.emptyList() : messages);
  }

  public void sendMessage(Conversation conversation) {
    long timestamp = System.currentTimeMillis();
    Message message = new Message("3" + timestamp, new User("3", "Tyrion Lannister", "",
        "https://pbs.twimg.com/profile_images/668279339838935040/8sUE9d4C.jpg"),
        timestamp, false, new TextPayLoad("Tell me blonde who never burns"));

    super.sendMessage(message, conversation);
  }
}
