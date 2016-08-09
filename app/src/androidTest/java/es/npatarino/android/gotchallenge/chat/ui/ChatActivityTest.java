package es.npatarino.android.gotchallenge.chat.ui;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.chat.conversation.di.ConversationComponent;
import es.npatarino.android.gotchallenge.chat.conversation.di.ConversationModule;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import org.junit.Rule;
import org.junit.Test;

public class ChatActivityTest {

    @Rule
    public ActivityTestRule<ChatActivity> activityTestRule =
            new ActivityTestRule<>(ChatActivity.class, true, false);

    @Rule public DaggerMockRule<ConversationComponent> daggerRule =
            new DaggerMockRule<>(ConversationComponent.class, new ConversationModule()).set(
                    new DaggerMockRule.ComponentSetter<ConversationComponent>() {
                        @Override public void setComponent(ConversationComponent component) {
                            GotChallengeApplication app =
                                    (GotChallengeApplication) InstrumentationRegistry.getInstrumentation()
                                            .getTargetContext()
                                            .getApplicationContext();

                        }
                    });

    @Test
    public void
    show_conversation_name_in_toolbar_title_when_init_activity() throws Exception {

    }

    @Test
    public void
    show_conversation_users_in_toolbar_subtitle_when_init_activity() throws Exception {

    }

    @Test
    public void
    show_conversation_image_in_toolbar_subtitle_when_init_activity() throws Exception {

    }

    @Test
    public void
    move_to_detail_conversation_activity_when_click_in_toolbar() throws Exception {

    }

    @Test
    public void
    show_my_message_when_send_a_message() throws Exception {

    }

    @Test
    public void
    show_messages_in_order_from_others_when_there_are_messages() throws Exception {

    }

    @Test
    public void
    show_messages_in_order_from_others_and_me_when_send_messages_and_receive_messages() throws Exception {

    }
}