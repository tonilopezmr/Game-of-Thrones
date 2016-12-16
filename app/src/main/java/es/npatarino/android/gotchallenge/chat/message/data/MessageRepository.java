package es.npatarino.android.gotchallenge.chat.message.data;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import es.npatarino.android.gotchallenge.chat.conversation.model.Conversation;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.chat.message.model.Message;
import es.npatarino.android.gotchallenge.chat.message.model.Payload;
import es.npatarino.android.gotchallenge.chat.message.viewmodel.ImagePayload;
import es.npatarino.android.gotchallenge.chat.message.viewmodel.StickerPayLoad;
import es.npatarino.android.gotchallenge.chat.message.viewmodel.TextPayLoad;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MessageRepository implements MessageDomain.Repository {

  private HashMap<String, PublishSubject<Message>> messagesPublisher;
  private HashMap<String, List<Message>> converMessages;
  private Context context;

  public MessageRepository(Context context) {
    this.context = context;
    messagesPublisher = new HashMap<>();
    converMessages = new HashMap<>();
    initConver();
  }

  private void initConver() {
    Gson gson = new Gson();
    InputStream inputStream = null;
    try {
      inputStream = context.getAssets().open("lannister_conver.json");
      Reader reader = new InputStreamReader(inputStream, "UTF-8");
      MessageEntity[] parsedList = gson.fromJson(reader, MessageEntity[].class);
      converMessages.put("50fab25b", transform(parsedList));

      inputStream = context.getAssets().open("stark_conver.json");
      Reader reader2 = new InputStreamReader(inputStream, "UTF-8");
      MessageEntity[] parsedList2 = gson.fromJson(reader2, MessageEntity[].class);
      converMessages.put("f96537a9", transform(parsedList2));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @NonNull
  private List<Message> transform(MessageEntity[] parsedList) {
    List<Message> messages = new ArrayList<>();
    for (MessageEntity messageEntity : parsedList) {
      Payload payload = new TextPayLoad(messageEntity.getMessage());

      String sticker = messageEntity.getSticker();
      String imageUrl = messageEntity.getImageUrl();

      if (imageUrl != null && !imageUrl.isEmpty()) {
        payload = new ImagePayload(imageUrl, messageEntity.getMessage());
      } else if (sticker != null && !sticker.isEmpty()) {
        payload = new StickerPayLoad("/data/user/0/es.npatarino.android.gotchallenge.debug/cache/"
            + messageEntity.getSticker());
      }

      messages.add(new Message(messageEntity.getId(),
          messageEntity.getUserFrom(),
          messageEntity.getTimestamp(),
          messageEntity.isFromMe(),
          payload));
    }
    return messages;
  }

  @Override
  public Observable<List<Message>> getMessages(Conversation conversation) {
    return Observable.empty(); //TODO NOT implemented
  }

  @Override
  public Observable<Void> sendMessage(Message message, Conversation conversation) {
    getPublisher(conversation).onNext(message);
    return Observable.empty();
  }

  @Override
  public Observable<Message> subscribeToMessages(Conversation conversation) {
    List<Message> messages1 = converMessages.get(conversation.getId());
    List<Message> messages = messages1 == null ? new ArrayList<>() : messages1;

    Observable<Message> messageObservable = Observable
        .interval(new Random().nextInt(2000), TimeUnit.MILLISECONDS)
        .map(i -> messages.get(i.intValue()))
        .take(messages.size());

    return getPublisher(conversation).mergeWith(messageObservable);
  }

  private PublishSubject<Message> getPublisher(Conversation conversation) {
    String id = conversation.getId();
    if (!messagesPublisher.containsKey(id)) {
      PublishSubject<Message> publisher = PublishSubject.create();
      messagesPublisher.put(id, publisher);
    }

    return messagesPublisher.get(id);
  }
}
