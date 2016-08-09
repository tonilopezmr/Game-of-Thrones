package es.npatarino.android.gotchallenge.chat.di;

import dagger.Subcomponent;
import es.npatarino.android.gotchallenge.chat.ui.ChatActivity;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = ChatActivityModule.class)
public interface ChatActivityComponent {

    ChatActivity inject(ChatActivity activity);
}
