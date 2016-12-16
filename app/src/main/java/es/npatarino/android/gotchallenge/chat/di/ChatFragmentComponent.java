package es.npatarino.android.gotchallenge.chat.di;

import dagger.Subcomponent;
import es.npatarino.android.gotchallenge.chat.ui.ChatFragment;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = ChatFragmentModule.class)
public interface ChatFragmentComponent {

  ChatFragment inject(ChatFragment fragment);
}
