package es.npatarino.android.gotchallenge.chat.conversation.view;

import es.npatarino.android.gotchallenge.base.Mvp;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;

public interface ConversationView extends Mvp.View{

    void show(Conversation conversation);
    void initChat();
}
