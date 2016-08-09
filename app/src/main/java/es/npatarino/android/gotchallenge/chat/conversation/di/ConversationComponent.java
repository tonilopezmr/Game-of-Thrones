package es.npatarino.android.gotchallenge.chat.conversation.di;

import dagger.Subcomponent;
import es.npatarino.android.gotchallenge.chat.di.ChatActivityComponent;
import es.npatarino.android.gotchallenge.chat.di.ChatActivityModule;
import es.npatarino.android.gotchallenge.chat.di.ChatScope;

@ChatScope
@Subcomponent(modules = ConversationModule.class)
public interface ConversationComponent {

    ChatActivityComponent plus(ChatActivityModule module);
}
