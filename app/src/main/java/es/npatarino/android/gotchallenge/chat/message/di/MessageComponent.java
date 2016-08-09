package es.npatarino.android.gotchallenge.chat.message.di;

import dagger.Subcomponent;
import es.npatarino.android.gotchallenge.chat.di.ChatFragmentComponent;
import es.npatarino.android.gotchallenge.chat.di.ChatFragmentModule;
import es.npatarino.android.gotchallenge.chat.di.ChatScope;

@ChatScope
@Subcomponent(modules = MessageModule.class)
public interface MessageComponent {

    ChatFragmentComponent plus(ChatFragmentModule module);
}
