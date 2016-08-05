package es.npatarino.android.gotchallenge.chat.conversation.view;

import es.npatarino.android.gotchallenge.base.Mvp;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;

public interface ConversationDetailView extends Mvp.View{

    void show(Conversation conversation);
}
