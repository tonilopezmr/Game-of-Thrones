package es.npatarino.android.gotchallenge.chat.conversation.view;

import es.npatarino.android.gotchallenge.base.Mvp;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;

import java.util.List;

public interface ConversationDetailView extends Mvp.View{

    void show(Conversation conversation);
    void showParticipants(List<User> userList);
    void showEmptyParticipantsCase();
}
