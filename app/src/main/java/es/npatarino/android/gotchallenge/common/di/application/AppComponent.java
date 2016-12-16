package es.npatarino.android.gotchallenge.common.di.application;

import dagger.Component;
import es.npatarino.android.gotchallenge.characters.di.CharacterComponent;
import es.npatarino.android.gotchallenge.characters.di.CharacterModule;
import es.npatarino.android.gotchallenge.chat.conversation.di.ConversationComponent;
import es.npatarino.android.gotchallenge.chat.conversation.di.ConversationModule;
import es.npatarino.android.gotchallenge.chat.message.di.MessageComponent;
import es.npatarino.android.gotchallenge.chat.message.di.MessageModule;
import es.npatarino.android.gotchallenge.houses.di.HouseComponent;
import es.npatarino.android.gotchallenge.houses.di.HouseModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {


  CharacterComponent plus(CharacterModule characterModule);

  HouseComponent plus(HouseModule houseModule);

  ConversationComponent plus(ConversationModule conversationModule);

  MessageComponent plus(MessageModule messageModule);
}
