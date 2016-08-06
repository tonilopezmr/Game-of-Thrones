package es.npatarino.android.gotchallenge.chat.di;

import dagger.Component;
import es.npatarino.android.gotchallenge.base.di.ActivityScope;
import es.npatarino.android.gotchallenge.base.di.components.ActivityComponent;
import es.npatarino.android.gotchallenge.base.di.components.AppComponent;
import es.npatarino.android.gotchallenge.base.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.conversation.domain.interactor.GetConversation;
import es.npatarino.android.gotchallenge.chat.conversation.presenter.ConversationPresenter;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.GetMessages;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SendMessage;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SubscribeToMessage;
import es.npatarino.android.gotchallenge.chat.message.presenter.MessagePresenter;
import es.npatarino.android.gotchallenge.chat.ui.ChatActivity;
import es.npatarino.android.gotchallenge.chat.ui.ChatFragment;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ChatModule.class, ActivityModule.class})
public interface ChatComponent extends ActivityComponent {

    void inject(ChatFragment fragment);
    void inject(ChatActivity activity);

    MessageDomain.Repository provideMessageRepository();

    SubscribeToMessage provideSubscribeToMessage();
    SendMessage provideSendMessage();
    GetMessages provideGetMessages();

    MessagePresenter provideMessagePresenter();

    //Conversation
    ConversationDomain.Repository provideConversationRepository();
    GetConversation provideGetConversation();
    ConversationPresenter provideConversationPresenter();
}
