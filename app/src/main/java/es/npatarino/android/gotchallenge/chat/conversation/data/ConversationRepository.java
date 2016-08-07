package es.npatarino.android.gotchallenge.chat.conversation.data;

import android.content.Context;
import com.google.gson.Gson;
import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import rx.Observable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

public class ConversationRepository implements ConversationDomain.Repository {

    private HashMap<String, Conversation> conversations;
    private Context context;

    public ConversationRepository(Context context) {
        this.context = context;
    }

    private void initConversations() throws Exception {
        Gson gson = new Gson();
        InputStream inputStream = context.getAssets().open("conversations.json");
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        Conversation[] parsedList = gson.fromJson(reader, Conversation[].class);
        for (Conversation conversation : parsedList) {
            conversations.put(conversation.getId(), conversation);
        }
    }

    @Override
    public Observable<Conversation> get(Conversation conversation) {
        return Observable.fromCallable(() -> {
            conversations = new HashMap<>();
            initConversations();
            Conversation conversation1 = conversations.get(conversation.getId());
            if (conversation1 == null) conversation1 = conversations.get("50fab25b");
            return conversation1;
        });
    }

}
